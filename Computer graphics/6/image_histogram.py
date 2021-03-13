import argparse
import matplotlib.pylab as plt
from PIL import Image

def draw_hist(file_name, hist_name, arr) -> None:
    plt.title(hist_name)
    plt.xlabel('Tonal range')
    plt.ylabel('Number of pixels')
    max_y = max(arr)
    max_y += max_y // 10
    plt.xlim([-0.1, 255])
    plt.ylim([-0.1, max_y])
    plt.xticks(range(0, 256, 15))
    plt.bar(range(256), arr, width=1, color='blue')
    plt.savefig(file_name)
    plt.close()


def color_count(image_pixels, channel):
    colors = [0] * 256
    for count, rgb in image_pixels:
        if channel == 3:
            colors[round(0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2])] += count
        else:
            colors[rgb[channel]] += count
    return colors
def start_processing(file_name):
    image = Image.open(file_name)
    image_height, image_width = image.size
    image_pixels = image.getcolors(image_height * image_width)
    return image_pixels
def parse():
    parser = argparse.ArgumentParser()
    parser.add_argument('-name')
    parser.add_argument('-path')
    return parser.parse_args()
def main():
    args = parse()
    if args.name and args.path:
        image_pixels = start_processing(file_name=args.name)
        r_shades = color_count(image_pixels=image_pixels, channel=0)
        g_shades = color_count(image_pixels=image_pixels, channel=1)
        b_shades = color_count(image_pixels=image_pixels, channel=2)
        luminosity_shades = color_count(image_pixels=image_pixels, channel=3)
        rgb_shades = [round((r_shades[i] + g_shades[i] + b_shades[i]) / 3) for i in range(256)]
        draw_hist(file_name=args.path + "redHistogram.png", hist_name="red channel histogram", arr=r_shades)
        draw_hist(file_name=args.path + "greenHistogram.png",hist_name="green channel histogram", arr=g_shades)
        draw_hist(file_name=args.path + "blueHistogram.png", hist_name="blue channel histogram", arr=b_shades)
        draw_hist(file_name=args.path + "luminosity_histogram.png",hist_name="luminosity histogram", arr=luminosity_shades)
        draw_hist(file_name=args.path + "rgbHistogram.png", hist_name=" ", arr=rgb_shades)
    else:
        raise AttributeError("Incorrect number of argument")
if __name__ == '__main__':
    main()
