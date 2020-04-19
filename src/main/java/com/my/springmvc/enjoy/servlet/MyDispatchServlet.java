package com.my.springmvc.enjoy.servlet;

import com.my.springmvc.enjoy.annotation.LtController;
import com.my.springmvc.enjoy.annotation.LtQualifier;
import com.my.springmvc.enjoy.annotation.LtRequestMapping;
import com.my.springmvc.enjoy.annotation.LtService;
import com.my.springmvc.enjoy.controller.MyController;
import com.my.springmvc.enjoy.handle.HandToolsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auto Created by IntelliJ IDEA.
 *
 * @author liutao
 * @since 2020/4/19
 */
public class MyDispatchServlet extends HttpServlet {
    List<String> classNames = new ArrayList<String>();
    Map<String, Object> beans = new HashMap<String, Object>();
    Map<String, Object> handMap = new HashMap<String, Object>();

    private static final long serialVersionUID = 8171415431097936588L;

    public MyDispatchServlet() {
        System.out.println("MyDispatchServlet constructor");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 扫描哪些类需要被实例化class（包及包以下的class）
        doScanPackage("com.my.springmvc.enjoy");
        for (String cName : classNames) {
            System.out.println("即将要被实例化的bean");
            System.out.println(cName);
            System.out.println("--------------");
        }

        // classNames 所有的bean全类名路径
        doInstance();

        // 依赖注入即把service反射创建出来的对象依赖注入到controller
        iodDi();

        // 建立URL和method的映射关系
        handMapping();
        for (Map.Entry<String, Object> entry : handMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        String path = uri.replaceAll(contextPath, "");

        Method method = (Method) handMap.get(path);

        MyController instance = (MyController) beans.get("/" + path.split("/")[1]);

        HandToolsService hand = (HandToolsService) beans.get("handTools");

        Object[] args = hand.hand(req, resp, method, beans);
        try {
            method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        classNames.clear();
        beans.clear();
        handMap.clear();
    }

    private void doScanPackage(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
        String fileStr = url.getFile();
        File file = new File(fileStr);

        String[] files = file.list();
        for (String path : files) {
            File filePath = new File(fileStr + path);
            if (filePath.isDirectory()) {
                doScanPackage(basePackage + "." + path);
            } else {
                // com/xx/XX.class
                classNames.add(basePackage + "." + filePath.getName());
            }
        }
    }

    private void doInstance() {
        if (classNames.size() <= 0) {
            System.out.println("doScanFailed...");
            return;
        }

        for (String className : classNames) {
            String cn = className.replaceAll(".class", "");

            try {
                Class<?> clazz = Class.forName(cn);
                //如果指定类型的注释存在于此元素上,否则返回false.即@LtController是否在某类上标注
                if (clazz.isAnnotationPresent(LtController.class)) {
                    LtController controller = clazz.getAnnotation(LtController.class);
                    Object instance = clazz.newInstance();

                    LtRequestMapping requestMapping = clazz.getAnnotation(LtRequestMapping.class);
                    String key = requestMapping.value();
                    beans.put(key, instance);
                } else if (clazz.isAnnotationPresent(LtService.class)) {
                    LtService service = clazz.getAnnotation(LtService.class);
                    Object instance = clazz.newInstance();
                    beans.put(service.value(), instance);
                } else {
                    continue;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void iodDi() {
        if (beans.entrySet().size() <= 0) {
            System.out.println("类没有实例化...");
            return;
        }

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            // 获取类，获取类声明的注解
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(LtController.class)) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(LtQualifier.class)) {
                        LtQualifier qualifier = field.getAnnotation(LtQualifier.class);
                        String value = qualifier.value();
                        field.setAccessible(true);
                        try {
                            field.set(instance, beans.get(value));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }
        }

    }

    private void handMapping() {
        if (beans.entrySet().size() <= 0) {
            return;
        }

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(LtController.class)) {
                LtRequestMapping requestMapping = clazz.getAnnotation(LtRequestMapping.class);
                String classPath = requestMapping.value();

                Method[] methods = clazz.getMethods();

                for (Method method : methods) {
                    if (method.isAnnotationPresent(LtRequestMapping.class)) {
                        LtRequestMapping mapping = method.getAnnotation(LtRequestMapping.class);
                        String methodUrl = mapping.value();

                        handMap.put(classPath + methodUrl, method);
                    } else {
                        continue;
                    }
                }
            }

        }
    }
}
