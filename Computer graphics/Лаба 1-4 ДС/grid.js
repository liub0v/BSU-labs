class Grid {

    pixel;

    constructor(pixel) {
        this.pixel = pixel;
    }

    setGrid(contextGrid, canvasX, canvasY) {
        contextGrid.clearRect(0, 0, 1200, 700);
        contextGrid.strokeStyle = '#ffffff';
        contextGrid.beginPath();
        for (let i = 0; i < canvasY; i += this.pixel.scale) {

            contextGrid.moveTo(0, i);
            contextGrid.lineTo(canvasX, i);

        }
        for (let i = 0; i < canvasX; i += this.pixel.scale) {

            contextGrid.moveTo(i, 0);
            contextGrid.lineTo(i, canvasY);

        }
        contextGrid.closePath();
        contextGrid.stroke();
    }

}