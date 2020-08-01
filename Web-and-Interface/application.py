from flask import Flask, render_template, request, Response, redirect, url_for
from flask_bootstrap import Bootstrap
from traffic_monitor import *
from cam_set import *

application = Flask(__name__)
Bootstrap(application)
check_settings()
VIDEO = VideoStreaming()

@application.route('/')
def home():
    TITLE = 'TRAFFIC MONITOR'
    return render_template('index.html', TITLE=TITLE)

@application.route('/video_feed')
def video_feed():
    #Video streaming route.
    return Response(
        VIDEO.show(),
        mimetype='multipart/x-mixed-replace; boundary=frame'
    )

@application.route('/request_preview_switch')
def request_preview_switch():
    VIDEO.preview = not VIDEO.preview
    print('*'*10, VIDEO.preview)
    return "nothing"

@application.route('/request_model_switch')
def request_model_switch():
    VIDEO.detect = not VIDEO.detect
    print('*'*10, VIDEO.detect)
    return "nothing"

@application.route('/reset_camera')
def reset_camera():
    STATUS = reset_settings()
    print('*'*10, STATUS)
    return "nothing"

if __name__ == '__main__':
    application.run(debug=True)