class Clipping {

    rectangle = new Rectangle();
    coordinates;
    //0-inside 1-outside 2-partial
    lines={
        coordinates:[],
        status: 0
    };

    constructor(rectangle, coordinates) {
        this.rectangle = rectangle;
        this.coordinates = coordinates;
    }

    setLines(){
        for (let i = 1; i < this.coordinates.length ; i++) {
            let x1 = this.coordinates[i-1][0];
            let y1 = this.coordinates[i-1][1];
            let x2 = this.coordinates[i][0];
            let y2 = this.coordinates[i][1];
            this.lines.coordinates.push([[x1,y1],[x2,y2]]);
            this.lines.status = 0;
        }
    }

    setStatus() {

        this.setLines();
        for (let i = 0; i < this.lines.length; i++) {

            let x1 = this.lines.coordinates[i][0][0];
            let y1 = this.lines.coordinates[i][0][1];
            let x2 = this.lines.coordinates[i][1][0];
            let y2 = this.lines.coordinates[i][1][1];

            if(x1<this.rectangle.getLeftSide() && y1<this.rectangle.getTopSide()){

            }

        }

    }

}
