import time
import cv2
import numpy as np

class Objectdata:
    def __init__(self):
        self.network = cv2.dnn.readNet(
            'models/yolov3.weights',
            'models/yolov3.cfg'
        )

        self.ClassName = []
        with open("models/coco.names", "r") as f:
            self.ClassName = f.read().rstrip('\n').split('\n')

        self.layer = [self.network.getLayerNames()[i[0] - 1] for i in self.network.getUnconnectedOutLayers()]
        
    def detectObj(self, frame):
        width = 320
        count = 0
        ht, wt, ch = frame.shape
        blob = cv2.dnn.blobFromImage(frame, 1/255, (width, width), (0,0,0), 1, crop=False)
        self.network.setInput(blob)
        outputs = self.network.forward(self.layer)
        classIds = []
        confidences = []
        boxes = []
        for out in outputs:
            for data in out:
                scores = data[5:]
                classId = np.argmax(scores)
                confidence = scores[classId]
                if confidence > 0.5:
                    w,h = int(data[2]*wt), int(data[3]*ht)
                    x,y = int((data[0]*wt)-w/2), int((data[1]*ht)-h/2)
                    boxes.append([x, y, w, h])
                    confidences.append(float(confidence))
                    classIds.append(classId)

        indices = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.3)
        font = cv2.FONT_HERSHEY_PLAIN
        for i in range(len(boxes)):
            if i in indices:
                x, y, w, h = boxes[i]
                onames = str(self.ClassName[classIds[i]])
                cv2.rectangle(frame, (x, y), (x+w,y+h), (255, 0 , 255), 2)
                cv2.putText(frame,f'{onames} {int(confidences[i]*100)}%',(x, y-8), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 0, 255), 2)
                if (onames=='car'):
                    count+=1
                    print(count)
                if(count>10):
                    cv2.putText(frame,'Traffic Occured',(500,500),cv2.FONT_HERSHEY_SIMPLEX,5, (0, 0, 255), 2)
        return frame


class VideoStreaming(object):
    def __init__(self):
        super(VideoStreaming, self).__init__()
        self.VIDEO = cv2.VideoCapture("vid.mp4")
        self.network = Objectdata()
        self._preview = True
        self._detect = False
        self._exposure = self.VIDEO.get(cv2.CAP_PROP_EXPOSURE)
        self._contrast = self.VIDEO.get(cv2.CAP_PROP_CONTRAST)

    @property
    def preview(self):
        return self._preview

    @preview.setter
    def preview(self, value):
        self._preview = bool(value)

    @property
    def detect(self):
        return self._detect

    @detect.setter
    def detect(self, value):
        self._detect = bool(value)
    
    @property
    def exposure(self):
        return self._exposure

    @exposure.setter
    def exposure(self, value):
        self._exposure = value
        self.VIDEO.set(cv2.CAP_PROP_EXPOSURE, self._exposure)
    
    @property
    def contrast(self):
        return self._contrast

    @contrast.setter
    def contrast(self, value):
        self._contrast = value
        self.VIDEO.set(cv2.CAP_PROP_CONTRAST, self._contrast)

    def show(self):
        while(self.VIDEO.isOpened()):
            ret, frame = self.VIDEO.read()
            
            if ret == True:
                if self._preview:
                    if self.detect:
                        frame = self.network.detectObj(frame)
                else:
                    frame = np.zeros((
                        int(self.VIDEO.get(cv2.CAP_PROP_FRAME_HEIGHT)),
                        int(self.VIDEO.get(cv2.CAP_PROP_FRAME_WIDTH))
                    ), np.uint8)
                    label = 'camera disabled'
                    H, W = frame.shape
                    font = cv2.FONT_HERSHEY_PLAIN
                    color = (255,255,255)
                    cv2.putText(frame, label, (W//2 - 100, H//2), font, 2, color, 2)
                
                frame = cv2.imencode('.jpg', frame)[1].tobytes()
                yield (b'--frame\r\n'b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')
                time.sleep(0.01)

            else:
                break
        print('off')
