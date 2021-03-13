class Drawing {
    pixel;

    constructor(pixel) {
        this.pixel = pixel;
    }

    drawLine(imageData, x0, y0, x1, y1, color) {

        if (x0 > x1) {
            [x0, x1] = [x1, x0];
            [y0, y1] = [y1, y0];
        }
        let dx = Math.abs(x1 - x0);
        let dy = Math.abs(y1 - y0);
        let sx = (x0 < x1) ? this.pixel.scale : -this.pixel.scale;// направление шага для x
        let sy = (y0 < y1) ? this.pixel.scale : -this.pixel.scale;
        let error = dx - dy;
        while (true) {
            this.pixel.setPixel(imageData, x0, y0, color);
            if ((x0 === x1) && (y0 === y1)) break;
            let error2 = 2 * error;
            if (error2 > -dy) {
                error -= dy;
                x0 += sx;
            }
            if (error2 < dx) {
                error += dx;
                y0 += sy;
            }
        }

    }

    drawEllipse(imageData, x0, y0, a, b, region, color) {

        const drawArc = (x0, y0, x, y, color) => {
            this.pixel.setPixel(imageData, x0 + x, y0 + y, color);
            this.pixel.setPixel(imageData, x0 - x, y0 + y, color);
            this.pixel.setPixel(imageData, x0 + x, y0 - y, color);
            this.pixel.setPixel(imageData, x0 - x, y0 - y, color);
        }

        const ellipse = () => {
            let dx, dy, d1, d2, x, y;
            x = 0;
            y = b;
            // Initial decision parameter of region 1
            d1 = (b * b) - (a * a * b) +
                (0.25 * a * a);
            dx = 2 * b * b * x;
            dy = 2 * a * a * y;
            // For region 1
            while (dx < dy) {

                // Print points based on 4-way symmetry

                drawArc(x0, y0, x, y, color);
                // Checking and updating value of
                // decision parameter based on algorithm
                if (d1 < 0) {
                    x += this.pixel.scale;
                    dx = dx + (2 * b * b) * this.pixel.scale;
                    d1 = (d1 + dx + (b * b))
                } else {
                    x += this.pixel.scale;
                    y -= this.pixel.scale;
                    dx = dx + (2 * b * b) * this.pixel.scale;
                    dy = dy - (2 * a * a) * this.pixel.scale;
                    d1 = (d1 + dx - dy + (b * b));
                }
            }

            // Decision parameter of region 2
            d2 = ((b * b) * ((x + 0.5) * (x + 0.5))) + ((a * a) * ((y - 1) * (y - 1)))
                - (a * a * b * b);

            // Plotting points of region 2
            while (y >= 0) {

                // Print points based on 4-way symmetry
                drawArc(x0, y0, x, y, color);
                // Checking and updating parameter
                // value based on algorithm
                if (d2 > 0) {
                    y -= this.pixel.scale;
                    dy -= 2 * a * a * this.pixel.scale;
                    d2 += ((a * a) - dy) * this.pixel.scale;
                } else {
                    y -= this.pixel.scale;
                    x += this.pixel.scale;
                    dx += (2 * b * b) * this.pixel.scale;
                    dy -= (2 * a * a) * this.pixel.scale;
                    d2 += ((dx - dy) + (a * a)) * this.pixel.scale;
                }
            }

        }

        ellipse();


    }

    drawCircle(imageData, x0, y0, radius, color) {

        let x = 0;
        let y = radius;
        let delta = this.pixel.scale - 2 * radius;
        let error = 0;
        while (y >= 0) {
            this.pixel.setPixel(imageData, x0 + x, y0 + y, color);
            this.pixel.setPixel(imageData, x0 + x, y0 - y, color);
            this.pixel.setPixel(imageData, x0 - x, y0 + y, color);
            this.pixel.setPixel(imageData, x0 - x, y0 - y, color);
            error = this.pixel.scale * (delta + y) - this.pixel.scale;
            if (delta < 0 && error <= 0) {
                x += this.pixel.scale;
                delta += this.pixel.scale * x + this.pixel.scale;
                continue
            }
            error = 2 * (delta - x) - this.pixel.scale;
            if (delta > 0 && error > 0) {
                y -= this.pixel.scale;
                delta += this.pixel.scale - this.pixel.scale * y;
                continue;
            }
            x += this.pixel.scale;
            delta += this.pixel.scale * (x - y);
            y -= this.pixel.scale;
        }
    }


}