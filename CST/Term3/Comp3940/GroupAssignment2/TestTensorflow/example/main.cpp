#include "facedetectcnn.h"

#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>

#include <filesystem>
#include <iomanip>
#include <iostream>
#include <optional>
#include <vector>

namespace{

    std::optional<std::filesystem::path>
    find_image(const std::filesystem::path &start_dir,
               const std::string &filename){
        std::filesystem::path current = std::filesystem::absolute(start_dir);

        for(int i = 0; i < 3; ++ i){
            std::filesystem::path candidate = current / filename;
            if(std::filesystem::exists(candidate)){
                return candidate;
            }
            if(! current.has_parent_path()){
                break;
            }
            current = current.parent_path();
        }
        return std::nullopt;
    }

    void draw_face_rect(cv::Mat &image, const FaceRect &face){
        cv::rectangle(image,
                      cv::Rect(face.x, face.y, face.w, face.h),
                      cv::Scalar(0, 255, 0), 2);

        for(int i = 0; i < 5; ++ i){
            cv::circle(image,
                       cv::Point(face.lm[2 * i], face.lm[2 * i + 1]),
                       2,
                       cv::Scalar(0, 0, 255),
                       cv::FILLED);
        }
    }

}

int main(){
    /* ==================================== Imgs ====================================== */
    const std::vector<std::string> image_names{"test1.jpg", "test2.jpg", "test3.jpg"};
    /* ================================================================================ */
    const std::filesystem::path start_dir = std::filesystem::current_path();

    std::vector<std::filesystem::path> resolved_paths;
    resolved_paths.reserve(image_names.size());
    for(const auto &name: image_names){
        auto found = find_image(start_dir, name);
        if(! found){
            std::cerr << "Image not found: " << name << std::endl;
            continue;
        }
        resolved_paths.push_back(*found);
    }

    if(resolved_paths.empty()){
        std::cerr << "No test images were located. Ensure imgs are in the project root."
                  << std::endl;
        return 1;
    }

    std::filesystem::path output_dir = std::filesystem::absolute(start_dir / "outputs");
    std::error_code ec;
    std::filesystem::create_directories(output_dir, ec);

    for(const auto &image_path: resolved_paths){
        cv::Mat image = cv::imread(image_path.string(), cv::IMREAD_COLOR);
        if(image.empty()){
            std::cerr << "Failed to read image: " << image_path << std::endl;
            continue;
        }

        std::cout << "Processing image: " << image_path << std::endl;

        std::vector<FaceRect> faces = objectdetect_cnn(image.data,
                                                       image.cols,
                                                       image.rows,
                                                       static_cast<int>(image.step));

        if(faces.empty()){
            std::cout << "No faces detected." << std::endl;
        } else{
            std::cout << "Detected " << faces.size() << " faces." << std::endl;
        }

        for(size_t idx = 0; idx < faces.size(); ++ idx){
            const FaceRect &face = faces[idx];
            std::cout << "  Face " << idx + 1 << ": score=" << std::fixed
                      << std::setprecision(3)
                      << face.score << " bbox=[" << face.x << ", " << face.y
                      << ", " << face.w << ", " << face.h << "]" << std::endl;
            draw_face_rect(image, face);
        }

        std::filesystem::path output_path =
                output_dir / (image_path.stem().string() + "_result" +
                              image_path.extension().string());
        if(! cv::imwrite(output_path.string(), image)){
            std::cerr << "Failed to save detection result: " << output_path << std::endl;
        } else{
            std::cout << "Result saved to: " << output_path << std::endl;
        }
    }

    return 0;
}
