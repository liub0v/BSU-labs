class Pixel {
    scale;

    constructor(scale) {
        this.scale = scale;
    }

    setPixel(imageData, x, y, c) {

        function drawPixel(scale, x, y, c) {

            function setElementaryPixel(index, c) {
                imageData.data[(index + 0)] = c.R;
                imageData.data[(index + 1)] = c.G;
                imageData.data[(index + 2)] = c.B;
                imageData.data[(index + 3)] = c.A;
            }

            let h = Math.trunc((scale - 1) / 2);
            let index = ((y) * imageData.width + (x)) * 4;
            setElementaryPixel(index, c);
            for (let i = 0; i < scale; i++) {
                index = ((y - h) * imageData.width + (x + i - h)) * 4;
                setElementaryPixel(index, c);
                index = ((y + i - h) * imageData.width + (x - h)) * 4;
                setElementaryPixel(index, c);
                index = ((y + h) * imageData.width + (x + i - h)) * 4;
                setElementaryPixel(index, c);
                index = ((y + i - h) * imageData.width + (x + h)) * 4;
                setElementaryPixel(index, c);
            }
        }

        for (let i = 1; i <= this.scale; i += 2) {
            drawPixel(i, x, y, c);
        }

    }
}