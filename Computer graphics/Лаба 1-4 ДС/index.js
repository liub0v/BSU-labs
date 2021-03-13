let canvas = document.getElementById('canvasMain');
let context = canvas.getContext('2d');
let imageData = context.createImageData(canvas.width, canvas.height);

let canvasGrid = document.getElementById('canvasGrid');
let contextGrid = canvasGrid.getContext('2d');

let canvasX = canvas.width;
let canvasY = canvas.height;
let pixel = new Pixel(9);
let coordinates =
    {
        draw: [],
        fill: [],
        clip: []
    }

let isGridON = false;
let drawing = new Drawing(pixel);
let filling = new Filling(pixel);
let point = new Point();
let grid = new Grid(pixel);


let clearBtn = document.getElementById("clearButton");
let drawLineBtn = document.getElementById("drawLine");
let drawCircleBtn = document.getElementById("drawCircle");
let drawEllipseBtn = document.getElementById("drawEllipse");
let slider = document.getElementById("slider_thickness");
let fillBtn = document.getElementById("fill")
let gridBtn = document.getElementById("grid");
let clipBtn = document.getElementById("clip");


function init() {


    slider.addEventListener('change', function () {
        pixel.scale = Number(this.value);

        if (isGridON) grid.setGrid(contextGrid, canvasX, canvasY);

    }, false);

    canvas.addEventListener("click", setDrawCoordinates, false);

    canvas.addEventListener("contextmenu", setFillCoordinates, false);

    clipBtn.onclick = function () {
        let clippingZone = new Rectangle(300, 300, 700, 500);
        let clipping = new Clipping(clippingZone, coordinates.clip);
        clippingZone.drawRect(contextGrid);
        clipping.clipLine();
    }

    clearBtn.onclick = function () {

        context.clearRect(0, 0, 1200, 700);
        coordinates.length = 0;
        for (let i = 3; i < imageData.data.length; i += 4) {
            imageData.data[i] = 0;
        }

    }

    drawEllipseBtn.onclick = function () {

        if (coordinates.draw.length) {
            let parameters_string = prompt("Enter a and b");
            let parameters_array = parameters_string.split(" ");
            let a = Math.trunc(Number(parameters_array[0]) / pixel.scale) * pixel.scale;
            let b = Math.trunc(Number(parameters_array[1]) / pixel.scale) * pixel.scale;
            let color = {
                R: Math.floor(Math.random() * 256),
                G: Math.floor(Math.random() * 256),
                B: Math.floor(Math.random() * 256),
                A: 255
            };
            let x0 = 0, y0 = 0;
            for (let i = 0; i < coordinates.length; i++) {
                x0 = coordinates.draw[i][0];
                y0 = coordinates.draw[i][1];
                drawing.drawEllipse(imageData, x0, y0, b, a, true, color);

            }
            context.putImageData(imageData, 0, 0);

            context.strokeStyle = '#ffffff';
            for (let i = 0; i < coordinates.draw.length; i++) {
                x0 = coordinates.draw[i][0];
                y0 = coordinates.draw[i][1];
                context.beginPath()
                context.ellipse(x0, y0, a, b, Math.PI / 2, 0, Math.PI * 2);
                context.closePath();
                context.stroke();
            }
            coordinates.draw.length = 0;
        }

    }

    drawLineBtn.onclick = function () {
        if (coordinates.draw.length) {
            let x0, y0, x1, y1;

            if (coordinates.draw.length > 1) {

                for (let i = 2; i < coordinates.draw.length + 1; i++) {
                    let color = {
                        R: Math.floor(Math.random() * 256),
                        G: Math.floor(Math.random() * 256),
                        B: Math.floor(Math.random() * 256),
                        A: 255
                    };
                    x0 = coordinates.draw[i - 2][0];
                    y0 = coordinates.draw[i - 2][1];
                    x1 = coordinates.draw[i - 1][0];
                    y1 = coordinates.draw[i - 1][1];

                    drawing.drawLine(imageData, x0, y0, x1, y1, color)
                }
            }
            context.putImageData(imageData, 0, 0);

            context.strokeStyle = '#fcfcfc';
            for (let i = 2; i < coordinates.draw.length + 1; i++) {
                x0 = coordinates.draw[i - 2][0];
                y0 = coordinates.draw[i - 2][1];
                x1 = coordinates.draw[i - 1][0];
                y1 = coordinates.draw[i - 1][1];
                context.beginPath()
                context.moveTo(x0, y0); //передвигаем перо
                context.lineTo(x1, y1); //рисуем линию
                context.closePath();
                context.stroke();
            }

            coordinates.draw.length = 0;

        }
    }

    drawCircleBtn.onclick = function () {
        if (coordinates.draw.length) {
            let x0, y0;
            let radius = pixel.scale * Math.trunc(Number(prompt("Enter radius")) / pixel.scale);
            let color = {
                R: Math.floor(Math.random() * 256),
                G: Math.floor(Math.random() * 256),
                B: Math.floor(Math.random() * 256),
                A: 255
            };
            for (let i = 0; i < coordinates.draw.length; i++) {
                x0 = coordinates.draw[i][0];
                y0 = coordinates.draw[i][1];
                drawing.drawCircle(imageData, x0, y0, radius, color);
            }
            context.putImageData(imageData, 0, 0);

            context.strokeStyle = '#fcfcfc';
            for (let i = 0; i < coordinates.draw.length; i++) {
                x0 = coordinates.draw[i][0];
                y0 = coordinates.draw[i][1];
                context.beginPath()
                context.arc(x0, y0, radius, 0, Math.PI * 2);
                context.closePath();
                context.stroke();
            }

            coordinates.draw.length = 0;
        }
    }

    fillBtn.onclick = function () {

        let color = {
            R: Math.floor(Math.random() * 256),
            G: Math.floor(Math.random() * 256),
            B: Math.floor(Math.random() * 256),
            A: 255
        };

        for (let i = 0; i < coordinates.fill.length; i++) {
            let x0 = coordinates.fill[i][0];
            let y0 = coordinates.fill[i][1];
            console.log(coordinates.fill[i][0] + ' ' + coordinates.fill[i][1]);
            let startColor = context.getImageData(x0, y0, 1, 1).data;
            filling.fillArea(canvasX, canvasY, imageData, x0, y0, startColor, color);

        }
        context.putImageData(imageData, 0, 0);
        coordinates.fill.length = 0;
    }

    gridBtn.onclick = function () {

        if (!isGridON) {
            isGridON = true;
            grid.setGrid(contextGrid, canvasX, canvasY);
        } else {
            isGridON = false;
            contextGrid.clearRect(0, 0, 1200, 700);
        }

    }


    function setFillCoordinates() {

        let x = getMouseCoordinates(event, canvas).x;
        let y = getMouseCoordinates(event, canvas).y;
        coordinates.fill.push([x, y]);

    }

    function setDrawCoordinates(event) {

        let x = getMouseCoordinates(event, canvas).x;
        let y = getMouseCoordinates(event, canvas).y;
        coordinates.clip.push([x, y]);
        coordinates.draw.push([x, y]);
        point.drawPoint(coordinates.draw, context);

    }


}

function getMouseCoordinates(event, canvas) {

    let rect = canvas.getBoundingClientRect();
    let x = (event.clientX - rect.left) / (rect.right - rect.left) * canvas.width;
    let y = (event.clientY - rect.top) / (rect.bottom - rect.top) * canvas.height;

    return {
        x: Math.round(Math.trunc(x / pixel.scale) * pixel.scale + Math.trunc(pixel.scale / 2)),
        y: Math.round(Math.trunc(y / pixel.scale) * pixel.scale + Math.trunc(pixel.scale / 2))
    };

}

init();

document.oncontextmenu = function () {
    return false;
};

function outputUpdate(thickness) {

    let output = document.querySelector('#value_thickness');
    output.value = thickness;
    output.style.left = (thickness - 2) * 20 - thickness - 5 + 'px';

}



