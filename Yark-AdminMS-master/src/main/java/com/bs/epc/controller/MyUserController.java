package com.bs.epc.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.epc.common.MyConstast;
import com.bs.epc.common.MyUserExcelParse;
import com.bs.epc.entity.MyDept;
import com.bs.epc.entity.MyUser;
import com.bs.epc.service.MyIDeptService;
import com.bs.epc.service.MyIUserService;
import com.bs.epc.vo.MyUserExcelVo;
import com.bs.epc.vo.MyUserVo;
import com.bs.sys.common.*;
import com.bs.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("epc")
public class MyUserController {

    @Autowired
    private MyIUserService myIUserService;

    @Autowired
    private MyIDeptService myIDeptService;

    /**
     * 查询所有居民
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(MyUserVo myUserVo) {
        IPage<MyUser> page = new Page<MyUser>(myUserVo.getPage(), myUserVo.getLimit());
        QueryWrapper<MyUser> queryWrapper = new QueryWrapper<MyUser>();
        queryWrapper.eq(StringUtils.isNotBlank(myUserVo.getDocumentphone()), "documenttype", myUserVo.getDocumentphone()).or().eq(StringUtils.isNotBlank(myUserVo.getCode()), "code", myUserVo.getCode());
        queryWrapper.eq(StringUtils.isNotBlank(myUserVo.getDomicile()), "domicile", myUserVo.getDomicile());

        //查询系统用户
//        queryWrapper.eq("type", MyConstast.USER_TYPE_NORMAL);
//        queryWrapper.eq(myUserVo.getDeptid() != null, "deptid", myUserVo.getDeptid());
        myIUserService.page(page, queryWrapper);

        //将所有居民数据放入list中
        List<MyUser> list = page.getRecords();
        for (MyUser myUser : list) {
            Integer deptid = myUser.getDeptid();
            if (deptid != null) {
                //先从缓存中去取，如果缓存中没有就去数据库中取
                MyDept one = myIDeptService.getById(deptid);
                //设置myUser的区域名称
                myUser.setDeptname(one.getName());
            }
            Integer mgr = myUser.getMgr();
            if (mgr != null) {
                MyUser one = myIUserService.getById(mgr);
                //设置myUser的上级名称
                myUser.setLeadername(one.getName());
            }
        }
        return new DataGridView(page.getTotal(), list);
    }

    /**
     * 加载排序码
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String, Object> loadUserMaxOrderNum() {
        Map<String, Object> map = new HashMap<String, Object>();
        QueryWrapper<MyUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<MyUser> page = new Page<>(1, 1);
        List<MyUser> list = myIUserService.page(page, queryWrapper).getRecords();
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrdernum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 根据区域ID查询居民
     */
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUserByDeptId(Integer deptid){
        QueryWrapper<MyUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptid!=null,"deptid",deptid);
        queryWrapper.eq("available",MyConstast.AVAILABLE_TRUE);
        List<MyUser> list = myIUserService.list(queryWrapper);
        return new DataGridView(list);
    }

    /**
     * 添加居民信息
     * @param myUserVo
     * @return
     */
    @RequestMapping("addUser")
    public ResultObj addUser(MyUserVo myUserVo){
        try{
            //设置类型
            myUserVo.setType(MyConstast.USER_TYPE_NORMAL);
            //设置盐
            String salt = IdUtil.simpleUUID().toUpperCase();
            myUserVo.setSalt(salt);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 接收批量导入用户的excel表，返回存储路径
     */
    @RequestMapping("excelupload")
    public Map<String,Object> excelupload(MultipartFile mf){
        //1.得到文件名
        String oldName = mf.getOriginalFilename();
        //2.根据旧的文件名生成新的文件名
        String newName=AppFileUtils.createNewFileName(oldName);
        //3.得到当前日期的字符串
        String dirName= DateUtil.format(new Date(), "yyyy-MM-dd");
        //4.构造文件夹
        File dirFile=new File(AppFileUtils.UPLOAD_PATH,dirName);
        //5.判断当前文件夹是否存在
        if(!dirFile.exists()) {
            //如果不存在则创建新文件夹
            dirFile.mkdirs();
        }
        //6.构造文件对象
        File file=new File(dirFile.getAbsolutePath(), newName);
        //7.把mf里面的图片信息写入file
        try {
            mf.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("path",dirName+"/"+newName);
        return map;
    }

    /**
     * 把excel转换到数据库
     */
    @RequestMapping("batchusers")
    public ResultObj batchUsers(String excelpath) {
        try {

            List<MyUserExcelVo> list = MyUserExcelParse.myUserExcelVos(new File(AppFileUtils.UPLOAD_PATH + "/" + excelpath));
            MyUser user = null;
            for (MyUserExcelVo myUserExcelVo : list) {
                user = new MyUser();
                //根据户籍所在地查询
                QueryWrapper<MyDept> queryWrapperD = new QueryWrapper<>();
                queryWrapperD.eq(myUserExcelVo.getDomicile() != null, "name", myUserExcelVo.getDomicile());
                queryWrapperD.eq("available", MyConstast.AVAILABLE_TRUE);
                List<MyUser> userList = myIUserService.list();
                //设置用户信息
                System.out.println("-----------------------------"+myUserExcelVo.toString()+"----------"+userList.size());
//                user.setName(myUserExcelVo.getName());
//                user.setCode(myUserExcelVo.getCode());
//                user.setDomicile(myUserExcelVo.getDomicile());
//                user.setDocumenttype(myUserExcelVo.getDocumenttype());
//                user.setDocumentphone(myUserExcelVo.getDocumentphone());
//                user.setPhone(myUserExcelVo.getPhone());
//                user.setAddress(myUserExcelVo.getAddress());
//                user.setHousehold(myUserExcelVo.getHousehold());
//                user.setRelation(myUserExcelVo.getRelation());
//                user.setRemark(myUserExcelVo.getRemark());
                //存入数据库
                QueryWrapper<MyUser> queryWrapperE = new QueryWrapper<>();
                queryWrapperE.eq(myUserExcelVo.getName() != null, "name", myUserExcelVo.getName());
                queryWrapperE.eq("available", MyConstast.AVAILABLE_TRUE);
                MyUser userExist = myIUserService.getOne(queryWrapperE);
                if (userExist != null) {
                    user.setId(userExist.getId());
                    myIUserService.updateById(user);
                } else {
                    myIUserService.save(user);
                }
            }
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 根据id查询一个用户
     * @param id
     * @return
     */
    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id){
        return new DataGridView(myIUserService.getById(id));
    }

    /**
     * 修改用户
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(MyUserVo myUserVo){
        try{
            myIUserService.updateById(myUserVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除用户
     */
    @RequestMapping("deleteUser/{id}")
    public ResultObj deleteUser(@PathVariable("id") Integer id){
        try{
            myIUserService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

//    /**
//     * 根据用户ID查询当前用户是否是其他用户的直属领导
//     * @param myUserId
//     * @return
//     */
//    @RequestMapping("queryMgrByUserId")
//    public ResultObj queryMgrByUserId(Integer myUserId){
//        Boolean isMgr = myIUserService.queryMgrByUserId(myUserId);
//        if (isMgr){
//            return ResultObj.DELETE_ERROR_NEWS;
//        }else {
//            return ResultObj.DELETE_QUERY;
//        }
//    }
}


