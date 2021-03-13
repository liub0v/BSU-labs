import argparse
from PIL import Image, ImageDraw
def grayscale(image_name, file_name, coefficient_red, coefficient_green, coefficient_blue):
    image, image_pixels, image_height, image_width, draw = start_processing(file_name=image_name)
    for i in range(image_height):
        for j in range(image_width):
            red, green, blue = image_pixels[i, j]
            gray = round(coefficient_red * red + coefficient_green * green + coefficient_blue * blue)
            draw.point((i, j), (gray, gray, gray))
    image.save(file_name, "JPEG")
    end_processing(draw=draw)
def start_processing(file_name):
    image = Image.open(file_name)
    image_height, image_width, = image.size
    image_pixels = image.load()
    draw = ImageDraw.Draw(image)
    return image, image_pixels, image_height, image_width, draw
def end_processing(draw):
    del draw
def parse():
    parser = argparse.ArgumentParser()
    parser.add_argument('-name')
    parser.add_argument('-path')
    return parser.parse_args()
def main():
    args = parse()
    if args.name and args.path:
        grayscale(image_name=args.name, file_name=args.path + "grayscale_1.jpg", coefficient_red=0.299,
                  coefficient_green=0.587, coefficient_blue=0.114)
        grayscale(image_name=args.name, file_name=args.path + "grayscale_2.jpg", coefficient_red=0.2126,
                  coefficient_green=0.7152, coefficient_blue=0.0722)
        grayscale(image_name=args.name, file_name=args.path + "grayscale_3.jpg", coefficient_red=0.2627,
                  coefficient_green=0.6780, coefficient_blue=0.0593)
    else:
        raise AttributeError("Incorrect number of argument")
if __name__ == '__main__':
    main()
