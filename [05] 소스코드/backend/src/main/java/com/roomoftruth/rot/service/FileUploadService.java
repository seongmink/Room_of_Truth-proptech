package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadService {

    public static final String SAVE_FOLDER = "/home/ubuntu/images/";
    public static final String IMAGE_URL = "http://k02b2031.p.ssafy.io:8080/images/";
//    public static final String SAVE_FOLDER = "C:/git/s02p31b203/[05] 소스코드/backend/src/main/resources/static/images/";
//    public static final String IMAGE_URL = "localhost:8080/images/";

    public final UserRepository userRepository;

    @Transactional
    public String fileUpload(MultipartFile image, int flag, int num) throws Exception {

        String saveUrl = "none";
        if (image != null) {
            String imageName = image.getOriginalFilename();
            String imageExtension = FilenameUtils.getExtension(imageName).toLowerCase();
            File destinationImage;
            String destinationImageName;
            String imageUrl = SAVE_FOLDER;

            SimpleDateFormat timeFormat = new SimpleDateFormat("yyMMddHHmmss");
            destinationImageName = timeFormat.format(new Date()) + "." + imageExtension;
            destinationImage = new File(imageUrl + destinationImageName);

            log.info("Image uploaded : {}", destinationImageName);

            image.transferTo(destinationImage);
            saveUrl = IMAGE_URL + destinationImageName;

            if (flag == 0) { // 거래 이력

            } else if (flag == 1) { // 상태 이력

            } else { // 공인중개사 사진 등록
                User user = userRepository.findByNum(num);
                user.getAgent().updatePicture(destinationImageName);
            }

            return destinationImageName;
        }

        return "default.png";
    }

}
