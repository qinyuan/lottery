function adjustImageWidth(target, width) {
    adjustImageByRate(target, width / target.width);
}

function adjustImageHeight(target, height) {
    adjustImageByRate(target, height / target.height);
}

function adjustImage(target, width, height) {
    adjustImageByRate(target, Math.min(width / target.width, height / target.height));
}

function adjustImageByRate(target, rate) {
    if (rate > 0 && rate < 1) {
        var newHeight = target.height * rate;
        var newWidth = target.width * rate;
        target.height = newHeight;
        target.width = newWidth;
    }
}
