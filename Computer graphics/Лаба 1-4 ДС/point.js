class Point {


    drawPoint(coordinates, context) {

        let start = Date.now();
        let delay = 200;
        let timer = setInterval(function () {
            let timePassed = Date.now() - start;
            if (timePassed >= delay) {
                clearInterval(timer);
                return;
            }
            draw(timePassed);


        }, 1);

        const draw = (timePassed) => {
            context.fillStyle = "white";
            context.beginPath();
            let length = coordinates.length;
            context.arc(coordinates[length - 1][0], coordinates[length - 1][1],
                timePassed / (delay / 7), 0, Math.PI * 2, false);
            context.closePath();
            context.fill();
        }


    }

}