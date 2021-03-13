import argparse
from utils import *


def create_binary_image_matrix(image_pixels, image_height, image_width):

    image_matrix = [[0] * image_width for _ in range(image_height)]

    for i in range(image_height):
        for j in range(image_width):
            image_matrix[i][j] = 1 if image_pixels[i, j][0] >= 127 else 0

    return image_matrix


def apply_median_filter(image_matrix, matrix_height, matrix_width, shift):
    image_matrix_with_filter = [[0] * matrix_width for _ in range(matrix_height)]
    expand_matrix, new_matrix_height, new_matrix_width = create_expand_matrix(image_matrix=image_matrix,
                                                                              matrix_height=matrix_height,
                                                                              matrix_width=matrix_width,
                                                                              shift=shift)
    for i in range(shift, new_matrix_height - shift):
        for j in range(shift, new_matrix_width - shift):
            sum_of_matrix = 0
            for pos_i in range(i - shift, i + shift + 1):
                for pos_j in range(j - shift, j + shift + 1):
                    sum_of_matrix += expand_matrix[pos_i][pos_j]
            image_matrix_with_filter[i - shift][j - shift] = 255 if sum_of_matrix > (
                    2 * shift + 1) ** 2 - sum_of_matrix else 0
    return image_matrix_with_filter


def parse():
    parser = argparse.ArgumentParser()
    parser.add_argument('-name', help='Path to image')
    parser.add_argument('-shift', help='Size for average operator')
    parser.add_argument('-path', help='Result path with name')
    return parser.parse_args()


def main():
    args = parse()

    if args.name and args.shift and args.path:

        image, image_height, image_width, image_pixels, draw = start_processing(file_name=args.name)

        image_matrix = create_binary_image_matrix(image_pixels=image_pixels,
                                                  image_height=image_height,
                                                  image_width=image_width)

        image_with_median_filter_matrix = apply_median_filter(image_matrix=image_matrix,
                                                              matrix_height=image_height,
                                                              matrix_width=image_width,
                                                              shift=int(args.shift))

        draw_image(image_matrix=image_with_median_filter_matrix,
                   image_height=image_height,
                   image_width=image_width,
                   image=image,
                   draw=draw,
                   file_name=args.path)

        end_processing(draw=draw)
    else:
        raise AttributeError("Incorrect number of argument")


if __name__ == '__main__':
    main()