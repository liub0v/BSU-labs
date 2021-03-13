class Filling {

    pixel;

    constructor(pixel) {
        this.pixel = pixel;
    }

    fillArea(canvasX, canvasY, imageData, x, y, startColor, color) {

        let points = [[x, y]];

        for (let i = 0; i < points.length; i++) {
            let x = points[i][0];
            let y = points[i][1];
            let index = ((y) * imageData.width + (x)) * 4
            if (x >= 0 && y >= 0 && x < canvasX && y < canvasY
                && imageData.data[index] === startColor[0]
                && imageData.data[index + 1] === startColor[1]
                && imageData.data[index + 2] === startColor[2]
                && imageData.data[index + 3] === startColor[3]) {
                this.pixel.setPixel(imageData, x, y, color);
                let index = points.length;
                points[index] = [x + this.pixel.scale, y];
                points[index + 1] = [x - this.pixel.scale, y];
                points[index + 2] = [x, y + this.pixel.scale];
                points[index + 3] = [x, y - this.pixel.scale];

            }

        }


    }
}