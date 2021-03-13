class Rectangle {

    x1 = 0;
    y1 = 0;
    x2 = 0;
    y2 = 0;

    constructor(x1, y1, x2, y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    getHeight() {
        return Math.abs(this.y2 - this.y1);
    }

    getWidth() {
        return Math.abs(this.x2 - this.x1);
    }

    getLeftSide() {
        return this.x1;
    }

    getRightSide() {
        return this.x2;
    }

    getTopSide() {
        return this.y1;
    }

    getBottomSide() {
        return this.y2;
    }

    drawRect(context) {
        // context.beginPath();
        //context.strokeStyle = 'red';
        //context.fillRect(this.x1,this.y1,this.getWidth(),this.getHeight());
        context.strokeRect(this.x1, this.y1, this.getWidth(), this.getHeight());


    }




}
