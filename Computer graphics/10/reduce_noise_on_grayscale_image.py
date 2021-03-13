import argparse
from utils import *
def apply_median_filter(image_matrix, matrix_height, matrix_width, shift):
    image_matrix_with_filter = [[0] * matrix_width for _ in range(matrix_height)]
    expand_matrix, new_matrix_height, new_matrix_width = create_expand_matrix(image_matrix=image_matrix,
                                                                              matrix_height=matrix_height,
                                                                              matrix_width=matrix_width,
                                                                              shift=shift)
    for i in range(shift, new_matrix_height - shift):
        for j in range(shift, new_matrix_width - shift):
            array = []
            for pos_i in range(i - shift, i + shift):
                for pos_j in range(j - shift, j + shift):
                    array.append(expand_matrix[pos_i][pos_j])
            array.sort()
            image_matrix_with_filter[i - shift][j - shift] = array[len(array) // 2]
    return image_matrix_with_filter
def create_average_operator(matrix_order, default_value=1):
    matrix = [[default_value] * matrix_order for _ in range(matrix_order)]
    matrix_sum = 0
    median_element = matrix_order // 2
    for i in range(matrix_order):
        for j in range(matrix_order):
            if i <= median_element and j <= median_element:
                matrix[i][j] **= i + j
            elif i <= median_element:
                matrix[i][j] = matrix[i][matrix_order - 1 - j]
            elif j <= median_element:
                matrix[i][j] = matrix[matrix_order - 1 - i][j]
            else:
                matrix[i][j] = matrix[matrix_order - 1 - i][matrix_order - 1 - j]
            matrix_sum += matrix[i][j]
    return matrix, matrix_sum
def apply_averaging_filter(image_matrix, matrix_height, matrix_width, shift):
    average_operator_matrix, matrix_sum = create_average_operator(2 * shift + 1)
    image_matrix_with_filter = [[0] * matrix_width for _ in range(matrix_height)]
    expand_matrix, new_matrix_height, new_matrix_width = create_expand_matrix(image_matrix=image_matrix,
                                                                              matrix_height=matrix_height,
                                                                              matrix_width=matrix_width,
                                                                              shift=shift)

    for i in range(shift, new_matrix_height - shift):
        for j in range(shift, new_matrix_width - shift):
            sub_matrix = [el[j - shift: j + shift + 1] for el in expand_matrix[i - shift: i + shift + 1]]
            image_matrix_with_filter[i - shift][j - shift] = round(
                compute_matrix_value(sub_matrix, average_operator_matrix, 3) / matrix_sum)
    return image_matrix_with_filter

def parse():
    parser = argparse.ArgumentParser()
    parser.add_argument('-name', help='Path to image')
    parser.add_argument('-a_shift', help='Size for average operator')
    parser.add_argument('-m_shift', help='Shift for median operator')
    parser.add_argument('-path', help='Path to directory where result will be saved')
    return parser.parse_args()
def main():
    args = parse()
    if args.name and args.a_shift and args.m_shift and args.path:
        image, image_height, image_width, image_pixels, draw = start_processing(args.name)
        image_matrix = create_image_matrix(image_pixels=image_pixels,
                                           image_height=image_height,
                                           image_width=image_width)

        image_with_averaging_filter_matrix = apply_averaging_filter(image_matrix=image_matrix,
                                                                    matrix_height=image_height,
                                                                    matrix_width=image_width,
                                                                    shift=int(args.a_shift))
        image_with_median_filter_matrix = apply_median_filter(image_matrix=image_matrix,
                                                              matrix_height=image_height,
                                                              matrix_width=image_width,
                                                              shift=int(args.m_shift))
        draw_image(image_matrix=image_with_averaging_filter_matrix,
                   image_height=image_height,
                   image_width=image_width,
                   image=image,
                   draw=draw,
                   file_name=args.path + "averaging_filter.jpg")
        draw_image(image_matrix=image_with_median_filter_matrix,
                   image_height=image_height,
                   image_width=image_width,
                   image=image,
                   draw=draw,
                   file_name=args.path + "median_filter.jpg")
        end_processing(draw)
    else:
        raise AttributeError("Incorrect number of argument")
if __name__ == '__main__':
    main()
