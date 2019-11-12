package org.jeecg.modules.fileManage.controller;

import com.sun.media.jai.codec.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.fileManage.service.IFileManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/sys/file")
public class FileView {

	@Value(value = "${jeecg.path.upload}")
	private String uploadpath;

    @Value(value = "${jeecg.path.tiffUrl}")
    private String tiffUrl;

    @Autowired
    private IFileManageService fileManageService;
	/**
	 * @Author 政辉
	 * @return
	 */
	@GetMapping("/403")
	public Result<?> noauth()  {
		return Result.error("没有权限，请联系管理员授权");
	}

	@PostMapping(value = "/upload")
	public Result<?> upload(HttpServletRequest request, HttpServletResponse response) {
		Result<?> result = new Result<>();
		try {
			String ctxPath = uploadpath;
			String fileName = null;
			String bizPath = "files";
			String relateType=request.getParameter("relateType");
            String relateId=request.getParameter("relateId");
			String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
            FileManage fileManage=new FileManage();
			File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
			if (!file.exists()) {
				file.mkdirs();// 创建文件根目录
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象


			String orgName = mf.getOriginalFilename();// 获取文件名
            String fileType=orgName.substring(orgName.lastIndexOf(".")+1, orgName.length());;
            fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
			String savePath = file.getPath() + File.separator + fileName;
            fileManage.setFileName(fileName);
            String viewPath=nowday+"\\"+fileName;
            fileManage.setPath(savePath);
            fileManage.setFileType(fileType);
            fileManage.setViewPath(viewPath);
            fileManage.setRelateType(relateType);
            fileManage.setRelateId(relateId);
            fileManageService.save(fileManage);
			File savefile = new File(savePath);
			FileCopyUtils.copy(mf.getBytes(), savefile);
			String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
			if (dbpath.contains("\\")) {
				dbpath = dbpath.replace("\\", "/");
			}
			String returnId=fileManage.getId();
			result.setMessage(returnId);
			result.setSuccess(true);
		} catch (IOException e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 预览图片
	 * 请求地址：http://localhost:8080/common/view/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
	 *
	 * @param request
	 * @param response
	 */
	@GetMapping(value = "/view/**")
	public void view(HttpServletRequest request, HttpServletResponse response) {
		// ISO-8859-1 ==> UTF-8 进行编码转换
		String imgPath = extractPathFromPattern(request);
		// 其余处理略
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			imgPath = imgPath.replace("..", "");
			if (imgPath.endsWith(",")) {
				imgPath = imgPath.substring(0, imgPath.length() - 1);
			}
			response.setContentType("image/jpeg;charset=utf-8");
			String localPath = uploadpath;
			String imgurl = localPath + File.separator + imgPath;
			inputStream = new BufferedInputStream(new FileInputStream(imgurl));
			outputStream = response.getOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}
			response.flushBuffer();
		} catch (IOException e) {
			log.error("预览图片失败" + e.getMessage());
			// e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

	}

	/**
	 * 下载文件
	 * 请求地址：http://localhost:8080/common/download/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
    	@GetMapping(value = "/download/**")
	    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ISO-8859-1 ==> UTF-8 进行编码转换
		String filePath = extractPathFromPattern(request);
		// 其余处理略
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			filePath = filePath.replace("..", "");
			if (filePath.endsWith(",")) {
				filePath = filePath.substring(0, filePath.length() - 1);
			}
			String localPath = uploadpath;
			String downloadFilePath = localPath + File.separator + filePath;
//            String downloadFilePath = "\\\\192.168.100.57\\upFiles\\files\\20190731\\yzx_1564541098257.pdf";

            File file = new File(downloadFilePath);
	         if (file.exists()) {
	        	 response.setContentType("application/force-download");// 设置强制下载不打开            
	 			response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"),"iso-8859-1"));
	 			inputStream = new BufferedInputStream(new FileInputStream(file));
	 			outputStream = response.getOutputStream();
	 			byte[] buf = new byte[1024];
	 			int len;
	 			while ((len = inputStream.read(buf)) > 0) {
	 				outputStream.write(buf, 0, len);
	 			}
	 			response.flushBuffer();
	         }

		} catch (Exception e) {
			log.info("文件下载失败" + e.getMessage());
			// e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	/**
	 * @功能：pdf预览Iframe
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/pdf/pdfPreviewIframe")
	public ModelAndView pdfPreviewIframe(ModelAndView modelAndView) {
		modelAndView.setViewName("pdfPreviewIframe");
		return modelAndView;
	}


    @PostMapping(value = "/delFile")
    public static void delFolder(HttpServletRequest request) {
	    String folderPath=request.getParameter("path");
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
// param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }
	/**
	  *  把指定URL后的字符串全部截断当成参数
	  *  这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
	 * @param request
	 * @return
	 */
	private static String extractPathFromPattern(final HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
	}

	/**
	 *
	 *按照idlist批量删除记录
	 *
	 * */
	@GetMapping(value = "/deleteByRelateId")
	public Result<?> deleteByRelateId(HttpServletRequest request, HttpServletResponse response) {
		Result<?> result = new Result<>();
		String relateId = request.getParameter("id");
		if (fileManageService.deleteByRelateId(relateId)) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 *
	 *按照idlist批量更新relateId
	 *
	 * */
	@GetMapping(value = "/upDateRelateId")
	public Result<?> updateRelatedId(HttpServletRequest request, HttpServletResponse response) {
		Result<?> result = new Result<>();
		String oldRelateId = request.getParameter("oldRelateId");

		String newRelateId = request.getParameter("newRelateId");
		fileManageService.updateRelatedId(oldRelateId, newRelateId);

		result.setSuccess(true);
		return result;

	}

    @GetMapping(value = "/viewTiff")
    public  List<String> viewTiff(String filePath,String viewPath){
        if (StringUtils.isNotBlank(viewPath)){
            viewPath= viewPath.substring(0, viewPath.lastIndexOf("."));
        }
        FileSeekableStream ss = null;
        List<String> tiffList=new ArrayList<>();
        try {
            ss = new FileSeekableStream(filePath);//以流形式取出tif文件数据
        } catch (IOException e) {
            System.out.println("Exception in " + filePath + " " + e.getMessage());
            e.printStackTrace();
        }
        String path,tiffName;
        path = filePath.substring(0, filePath.lastIndexOf("."));
        tiffName = filePath.substring(filePath.lastIndexOf("\\"),filePath.lastIndexOf("."));
        TIFFDecodeParam param0 = null;
        TIFFEncodeParam param = new TIFFEncodeParam();
        JPEGEncodeParam param1 = new JPEGEncodeParam();
        if(ss!=null){
            ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, param0);//将tif文件流转为image图片
            int count = 0;
            try {
                count = dec.getNumPages();//tif文件页数
            } catch (IOException e) {
                System.out.println("Exception in dec.getNumPages() " + e.getMessage());
                e.printStackTrace();
            }
            param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
            param.setLittleEndian(false); // Intel
            System.out.println("This TIF has " + count + " image(s)");
            for (int i = 0; i < count; i++) {//存储为jpeg图片

                RenderedImage page = null;
                File f = new File(path,"\\"+tiffName+"_" + i + ".jpg");
                try {
                    page = dec.decodeAsRenderedImage(i);//取出第i张image图片
                    if(!f.exists()){
                        f.getParentFile().mkdirs();
                        f.createNewFile();
                    }
                } catch (IOException e) {
                    System.out.println("Exception in File f or dec.decodeAsRenderedImage(i)" + e.getMessage());
                    e.printStackTrace();
                }
                if(f.exists() && page != null){//压缩图片并保存为JPEG格式
                    ParameterBlock pb = new ParameterBlock();
                    pb.addSource(page);
                    pb.add(f.toString());
                    tiffList.add(viewPath+"\\"+tiffName+"_" + i + ".jpg");
                    pb.add("JPEG");
                    pb.add(param1);
                    RenderedOp r = JAI.create("filestore",pb);
                    r.dispose();
                }
            }
        }
        return tiffList;
    }


    /***
     *
     */
    @GetMapping(value = "/downLoadFromUrl/**")
    public void downLoadFromUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String filePath = extractPathFromPattern(request);
        // 其余处理略
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            filePath = filePath.replace("..", "");
            if (filePath.endsWith(",")) {
                filePath = filePath.substring(0, filePath.length() - 1);
            }
			String downloadFilePath = tiffUrl + File.separator + filePath;
//            String downloadFilePath = "\\\\192.168.100.57\\upFiles\\files\\20190731\\yzx_1564541098257.pdf";

            File file = new File(downloadFilePath);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开            
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"),"iso-8859-1"));
                inputStream = new BufferedInputStream(new FileInputStream(file));
                outputStream = response.getOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                response.flushBuffer();
            }

        } catch (Exception e) {
            log.info("文件下载失败" + e.getMessage());
            // e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
