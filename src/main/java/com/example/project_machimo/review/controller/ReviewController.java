package com.example.project_machimo.review.controller;

import com.example.project_machimo.AttachImageVO;
import com.example.project_machimo.review.dao.ReviewDao;
import com.example.project_machimo.review.dto.Criteria;
import com.example.project_machimo.review.dto.PageDTO;
import com.example.project_machimo.review.dto.ReplyDto;
import com.example.project_machimo.review.dto.ReviewDto;
import com.example.project_machimo.review.service.ReviewService;
import com.example.project_machimo.utils.UploadFileUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
//@RestController
//@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService service;

//    @Value("${uploadPath}")
//    private String uploadPath;

    @RequestMapping("/list_old")
    public String list(Model model) {
        log.info("@# list");
        ArrayList<ReviewDto> list = service.list();
        model.addAttribute("list",list);
        return "review/list";
    }

    @RequestMapping("/list")
    public String list(Criteria cri, Model model) {
        log.info("@# list");
        log.info("@#cri =======>"+cri);
        log.info("@#getPageNum() =======>"+cri.getPageNum());
//        ArrayList<ReviewDto> list = service.list();
        model.addAttribute("list",service.list(cri));
        int total = service.getTotalCount();
        model.addAttribute("pageMaker", new PageDTO(total, cri));
        return "review/list";
    }
    @RequestMapping("/write_view")
    public String writeView() {
        log.info("@# writeView");
        return "review/write_view";
    }

    @RequestMapping("/write")
    public String write(@RequestParam HashMap<String, String> param) {
        log.info("@# write");
        service.write(param);
        return "redirect:list";
    }

    @RequestMapping("/content_view")
    public String contentView(@RequestParam HashMap<String, String> param, Model model) {
        log.info("@# contentView");
        ReviewDto dto = service.contentView(param);
        model.addAttribute("content_view", dto);
        service.updateCount(dto.getReviewId());
//        model.addAttribute("hit", hit);
//        content_view.jsp 에서 pageMaker를 가지고 페이징 처리
        model.addAttribute("pageMaker", param);
        return "review/content_view";
    }

//    수정완료후 list 로 이동
    @RequestMapping("/modify")
//    public String modify(@RequestParam HashMap<String, String> param) {
    public String modify(@RequestParam HashMap<String, String> param, @ModelAttribute("cri") Criteria cri,
                         RedirectAttributes rttr) {
        log.info("@# modify");
//        System.out.println("param.get(\"reviewContent\") = " + param.get("reviewContent"));
        service.modify(param);
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return "redirect:list";
    }

//    reviewId값으로 전의 값 받아와서 수정폼으로 넘어감
    @RequestMapping("/modify_view")
    public String modify_view(@RequestParam("reviewId") String reviewId ,Model model) {
//        public String modify_view(@RequestParam("reviewId") String reviewId ,Model model, @ModelAttribute("cri") Criteria cri,
//                RedirectAttributes rttr) {
        ReviewDto reviewDto = service.modify_view(reviewId);
        model.addAttribute("content_view",reviewDto);
//        rttr.addAttribute("pageNum",cri.getPageNum());
//        rttr.addAttribute("amount",cri.getAmount());
        return "review/modify_view";
    }

    @RequestMapping("/delete")
//    public String delete(@RequestParam("reviewId") HashMap<String, String> param) {
//    public String delete(@RequestParam("reviewId") String reviewId) {
    public String delete(@RequestParam("reviewId") String reviewId, @ModelAttribute("cri") Criteria cri,
                         RedirectAttributes rttr) {
        log.info("@# delete");

        service.delete(reviewId);
        rttr.addAttribute("pageNum",cri.getPageNum());
        rttr.addAttribute("amount",cri.getAmount());
        return "redirect:list";
    }

//    첨부파일 업로드
//    produce 속성 :전송되는 json데이터 인코딩해서 이미지 파일이름 한글이어도 안깨지도록 해줌
    @RequestMapping (value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    반환타입이 ResponseEntity 객체ㅡ http의 body 에 추가될 데이터는 List<AttachImageVO> 임
    public ResponseEntity<List<AttachImageVO>> uploadajaxAction(MultipartFile[] uploadFile){

        log.info("uploadAjaxAction");

        /* 이미지 파일 체크 */
        for(MultipartFile multipartFile: uploadFile) {

            File checkfile = new File(multipartFile.getOriginalFilename());
            String type = null;

            try {
//                probeContentType  : 파라미터로 전달받은 파일의 MIME TYPE을 문자열 반환
//                toPath() :File객체를 Path객체로 만들어줌
                type = Files.probeContentType(checkfile.toPath());
                log.info("MIME TYPE:"+ type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(!type.startsWith("image")) {

                List<AttachImageVO> list = null;
                return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);

            }

        }

        String uploadFolder = "C:\\upload";

//        날짜 폴더 경로
        LocalDate currentDate = LocalDate.now();
        String datePath = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", File.separator);


        /* 폴더 생성 */
        File uploadPath = new File(uploadFolder, datePath);

        if(uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }

        /* 이미저 정보 담는 객체 */
        List<AttachImageVO> list = new ArrayList();

        for(MultipartFile multipartFile : uploadFile) {

//            이미지 정보 객체
            AttachImageVO vo = new AttachImageVO();

            /* 파일 이름 */
            String uploadFileName = multipartFile.getOriginalFilename();
            vo.setFileName(uploadFileName);
            vo.setUploadPath(datePath);

            /* uuid 적용 파일 이름 */
            String uuid = UUID.randomUUID().toString();
            vo.setUuid(uuid);

            uploadFileName = uuid + "_" + uploadFileName;

            /* 파일 위치, 파일 이름을 합친 File 객체 */
            File saveFile = new File(uploadPath, uploadFileName);

            /* 파일 저장 */
            try {
                multipartFile.transferTo(saveFile);

//                thumnailator사용
                File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

                BufferedImage bo_image = ImageIO.read(saveFile);

                //비율
                double ratio = 3;
                //넓이 높이
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                Thumbnails.of(saveFile)
                        .size(width, height)
                        .toFile(thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(vo);
        }//for
//        http의 body에 추가될 데이터는 List<AttachImageVO>이고, 상태코드가 Ok(200)인 ResponseEntity 객체 생성
        ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);
        return result;
    }



    /////////////////////////////////////댓글관련//////////////////////////////////

    @RequestMapping(value="/replyList", method=RequestMethod.GET)
    @ResponseBody
    public List<ReplyDto> replyList(@RequestParam("reviewId")int reviewId){
        return service.getReply(reviewId);
    }
    @RequestMapping(value="/writeReply", method=RequestMethod.POST)
    public String writeReply(
            @RequestParam("id")int id,
            @RequestParam("replyid")int replyid,
            @RequestParam("contents")String contents) {
//
//    public String writeReply(@RequestParam HashMap<String,String> param,
//                             @RequestParam("reviewId")int reviewId) {

//        ReviewDto dto = service.addReply(param);
//        model.addAttribute("content_view", dto);
//        service.addReply(new ReplyDto(param));
        return "redirect:content_view?id=" +id;
    }




}
