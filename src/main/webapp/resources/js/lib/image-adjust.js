function adjustImageWidth(target, width) {
    retryUntilSuccess(function () {
        var realWidth = target.width;
        if (realWidth <= 0) {
            return false;
        } else {
            adjustImageByRate(target, width / realWidth);
            return true;
        }
    });
}

function adjustImageHeight(target, height) {
    retryUntilSuccess(function () {
        var realHeight = target.height;
        if (height <= 0) {
            return false;
        } else {
            adjustImageByRate(target, height / realHeight);
            return true;
        }
    });
}

function adjustImage(target, width, height) {
    retryUntilSuccess(function () {
        var realWidth = target.width;
        var realHeight = target.height;
        if (realWidth <= 0 || realHeight <= 0) {
            return false;
        } else {
            adjustImageByRate(target, Math.min(width / realWidth, height / realHeight));
            return true;
        }
    });
}

function adjustImageByRate(target, rate) {
    if (rate > 0 && rate < 1) {
        var newHeight = target.height * rate;
        var newWidth = target.width * rate;
        target.height = newHeight;
        target.width = newWidth;
    }
}

function retryUntilSuccess(retryFunction, retryTime, interval) {
    if (!retryTime) {
        retryTime = 10;
    }
    if (!interval) {
        interval = 50;
    }
    if (!retryFunction()) {
        var timer = setInterval(function () {
            if (retryTime <= 1 || retryFunction()) {
                clearInterval(timer);
            }
            retryTime--;
        }, interval);
    }

}
