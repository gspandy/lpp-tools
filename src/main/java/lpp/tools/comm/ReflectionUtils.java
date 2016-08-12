/**
 * 文件名：ReflectionUtils.java
 * 创建日期： 2016年8月10日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 服务端
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2016年8月10日
 *   修改人：lipanpan
 *   修改内容：
 */
package lpp.tools.comm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 功能描述：反射工具类
 */
public abstract class ReflectionUtils
{
    private static final Pattern CGLIB_RENAMED_METHOD_PATTERN = Pattern.compile("CGLIB\\$(.+)\\$\\d+");

    /***
     * 查询字段Field
     * @param clazz
     * @param name
     * @return
     */
    public static Field findField(Class<?> clazz, String name)
    {
        return findField(clazz, name, null);
    }

    /***
     * 查询字段Field
     * @param clazz
     * @param name
     * @param type
     * @return
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type)
    {
        AssertUtils.isNull(clazz, "Class must not be null");
        AssertUtils.isTrue(name != null || type != null, "Either name or type of the field must be specified");
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null)
        {
            // 获取类中所有的Field，包括public,protected,private
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields)
            {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType())))
                {
                    return field;
                }
            }
            // 获取父类
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /***
     * 设置target目标对象field字段值为value
     * @param field
     * @param target
     * @param value
     */
    public static void setField(Field field, Object target, Object value)
    {
        try
        {
            field.set(target, value);
        }
        catch (IllegalAccessException ex)
        {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
                + ex.getMessage());
        }
    }

    /***
     * 获取target对象，目标字段的值
     * @param field
     * @param target
     * @return
     */
    public static Object getField(Field field, Object target)
    {
        try
        {
            return field.get(target);
        }
        catch (IllegalAccessException ex)
        {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
                + ex.getMessage());
        }
    }

    /**
     * 查询类方法Method(不带参数)
     * @param clazz
     * @param name
     * @return
     */
    public static Method findMethod(Class<?> clazz, String name)
    {
        return findMethod(clazz, name, new Class[0]);
    }

    /***
     * 查询类方法Method(含参数类型匹配)
     * @param clazz
     * @param name
     * @param paramTypes
     * @return
     */
    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes)
    {
        AssertUtils.isNull(clazz, "Class must not be null");
        AssertUtils.isNull(name, "Method name must not be null");
        Class<?> searchType = clazz;
        while (searchType != null)
        {
            // getMethods()获取类中的所有public方法，getDeclaredMethods()获取public,protected,private方法
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (Method method : methods)
            {
                if (name.equals(method.getName())
                    && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes())))
                {
                    return method;
                }
            }
            // 获取父类
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /***
     * 获取类Class上所有的注解Annotation
     * @param clazz
     * @return
     */
    public static Annotation[] findClassAnnotation(Class<?> clazz)
    {
        AssertUtils.isNull(clazz, "Class must not be null");
        Class<?> searchType = clazz;
        return searchType.getAnnotations();
    }

    /***
     * 获取类Class上指定类型的注解Annotation
     * @param clazz
     * @param annotationClass
     * @return
     */
    public static <A extends Annotation> A findClassAnnotation(Class<?> clazz, Class<A> annotationClass)
    {
        AssertUtils.isNull(clazz, "Class must not be null");
        Class<?> searchType = clazz;
        return searchType.getAnnotation(annotationClass);
    }

    /***
     * 获取字段Field上所有的注解Annotation
     * @param field
     * @return
     */
    public static Annotation[] findFieldAnnotation(Field field)
    {
        AssertUtils.isNull(field, "Field must not be null");
        Field searchType = field;
        return searchType.getAnnotations();
    }

    /***
     * 获取字段Field上指定类型的注解Annotation
     * @param field
     * @param annotationClass
     * @return
     */
    public static <A extends Annotation> A findFieldAnnotation(Field field, Class<A> annotationClass)
    {
        AssertUtils.isNull(field, "Field must not be null");
        Field searchType = field;
        return searchType.getAnnotation(annotationClass);
    }

    /***
     * 获取方法Method上所有的注解Annotation
     * @param field
     * @return
     */
    public static Annotation[] findMethodAnnotation(Method method)
    {
        AssertUtils.isNull(method, "Method must not be null");
        Method searchType = method;
        return searchType.getAnnotations();
    }

    /***
     * 获取方法Method上指定类型的注解Annotation
     * @param method
     * @param annotationClass
     * @return
     */
    public static <A extends Annotation> A findMethodAnnotation(Method method, Class<A> annotationClass)
    {
        AssertUtils.isNull(method, "Method must not be null");
        Method searchType = method;
        return searchType.getAnnotation(annotationClass);
    }

    /***
     * 执行目标对象target中的方法Method(不带参数)
     * @param method
     * @param target
     * @return
     */
    public static Object invokeMethod(Method method, Object target)
    {
        return invokeMethod(method, target, new Object[0]);
    }

    /***
     * 执行目标对象target中的方法Method(带参数)
     * @param method
     * @param target
     * @param args
     * @return
     */
    public static Object invokeMethod(Method method, Object target, Object... args)
    {
        try
        {
            return method.invoke(target, args);
        }
        catch (Exception ex)
        {
            handleReflectionException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    /***
     * 执行目标对象target中的方法JDBC API Method(不带参数)
     * @param method
     * @param target
     * @return
     */
    public static Object invokeJdbcMethod(Method method, Object target) throws SQLException
    {
        return invokeJdbcMethod(method, target, new Object[0]);
    }

    /***
     * 执行目标对象target中的方法JDBC API Method(带参数)
     * @param method
     * @param target
     * @return
     */
    public static Object invokeJdbcMethod(Method method, Object target, Object... args) throws SQLException
    {
        try
        {
            return method.invoke(target, args);
        }
        catch (IllegalAccessException ex)
        {
            handleReflectionException(ex);
        }
        catch (InvocationTargetException ex)
        {
            if (ex.getTargetException() instanceof SQLException)
            {
                throw (SQLException) ex.getTargetException();
            }
            handleInvocationTargetException(ex);
        }
        throw new IllegalStateException("Should never get here");
    }

    /***
     * 反射异常处理
     * @param ex
     */
    public static void handleReflectionException(Exception ex)
    {
        if (ex instanceof NoSuchMethodException)
        {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException)
        {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException)
        {
            handleInvocationTargetException((InvocationTargetException) ex);
        }
        if (ex instanceof RuntimeException)
        {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /**
     * Handle the given invocation target exception. Should only be called if no
     * checked exception is expected to be thrown by the target method.
     * <p>
     * Throws the underlying RuntimeException or Error in case of such a root
     * cause. Throws an IllegalStateException else.
     * @param ex the invocation target exception to handle
     */
    public static void handleInvocationTargetException(InvocationTargetException ex)
    {
        rethrowRuntimeException(ex.getTargetException());
    }

    /**
     * Rethrow the given {@link Throwable exception}, which is presumably the
     * <em>target exception</em> of an {@link InvocationTargetException}. Should
     * only be called if no checked exception is expected to be thrown by the
     * target method.
     * <p>
     * Rethrows the underlying exception cast to an {@link RuntimeException} or
     * {@link Error} if appropriate; otherwise, throws an
     * {@link IllegalStateException}.
     * @param ex the exception to rethrow
     * @throws RuntimeException the rethrown exception
     */
    public static void rethrowRuntimeException(Throwable ex)
    {
        if (ex instanceof RuntimeException)
        {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error)
        {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /**
     * Rethrow the given {@link Throwable exception}, which is presumably the
     * <em>target exception</em> of an {@link InvocationTargetException}. Should
     * only be called if no checked exception is expected to be thrown by the
     * target method.
     * <p>
     * Rethrows the underlying exception cast to an {@link Exception} or
     * {@link Error} if appropriate; otherwise, throws an
     * {@link IllegalStateException}.
     * @param ex the exception to rethrow
     * @throws Exception the rethrown exception (in case of a checked exception)
     */
    public static void rethrowException(Throwable ex) throws Exception
    {
        if (ex instanceof Exception)
        {
            throw (Exception) ex;
        }
        if (ex instanceof Error)
        {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    /***
     * 判断指定访问method是否有声明exceptionType类型的异常
     * @param method
     * @param exceptionType
     * @return
     */
    public static boolean declaresException(Method method, Class<?> exceptionType)
    {
        AssertUtils.isNull(method, "Method must not be null");
        Class<?>[] declaredExceptions = method.getExceptionTypes();
        for (Class<?> declaredException : declaredExceptions)
        {
            if (declaredException.isAssignableFrom(exceptionType))
            {
                return true;
            }
        }
        return false;
    }

    /***
     * 判断字段是否为public static final类型
     * @param field
     * @return
     */
    public static boolean isPublicStaticFinal(Field field)
    {
        int modifiers = field.getModifiers();
        return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
    }

    /***
     * 判断方法是否为equals(Object obj)方法
     * @param method
     * @return
     */
    public static boolean isEqualsMethod(Method method)
    {
        if (method == null || !method.getName().equals("equals"))
        {
            return false;
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        return (paramTypes.length == 1 && paramTypes[0] == Object.class);
    }

    /***
     * 判断方法是否为hashCode()方法
     * @param method
     * @return
     */
    public static boolean isHashCodeMethod(Method method)
    {
        return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
    }

    /***
     * 判断方法是否为toString()方法
     * @param method
     * @return
     */
    public static boolean isToStringMethod(Method method)
    {
        return (method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0);
    }

    /***
     * 判断方法是否为Object内置方法
     * @param method
     * @return
     */
    public static boolean isObjectMethod(Method method)
    {
        try
        {
            Object.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return true;
        }
        catch (SecurityException ex)
        {
            return false;
        }
        catch (NoSuchMethodException ex)
        {
            return false;
        }
    }

    /**
     * Determine whether the given method is a CGLIB 'renamed' method, following
     * the pattern "CGLIB$methodName$0".
     * @param renamedMethod the method to check
     * @see net.sf.cglib.proxy.Enhancer#rename
     */
    public static boolean isCglibRenamedMethod(Method renamedMethod)
    {
        return CGLIB_RENAMED_METHOD_PATTERN.matcher(renamedMethod.getName()).matches();
    }

    /***
     * 设置字段可访问
     * @param field
     */
    public static void makeAccessible(Field field)
    {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
            .isFinal(field.getModifiers())) && !field.isAccessible())
        {
            field.setAccessible(true);
        }
    }

    /***
     * 设置方法可访问
     * @param method
     */
    public static void makeAccessible(Method method)
    {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
            && !method.isAccessible())
        {
            method.setAccessible(true);
        }
    }

    /***
     * 设置构造函数可访问
     * @param ctor
     */
    public static void makeAccessible(Constructor<?> ctor)
    {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
            && !ctor.isAccessible())
        {
            ctor.setAccessible(true);
        }
    }

    /***
     * 访问者模式：遍历clazz中所有方法method,并执行mc逻辑
     * @param clazz
     * @param fc
     * @throws IllegalArgumentException
     */
    public static void doWithMethods(Class<?> clazz, MethodCallback mc) throws IllegalArgumentException
    {
        doWithMethods(clazz, mc, null);
    }

    /***
     * 访问者模式：遍历clazz中符合mf过滤规则的Method,并执行mc逻辑
     * @param clazz
     * @param mc
     * @param mf
     * @throws IllegalArgumentException
     */
    public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf)
        throws IllegalArgumentException
    {
        // Keep backing up the inheritance hierarchy.
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods)
        {
            if (mf != null && !mf.matches(method))
            {
                continue;
            }
            try
            {
                mc.doWith(method);
            }
            catch (IllegalAccessException ex)
            {
                throw new IllegalStateException("Shouldn't be illegal to access method '" + method.getName() + "': "
                    + ex);
            }
        }
        if (clazz.getSuperclass() != null)
        {
            doWithMethods(clazz.getSuperclass(), mc, mf);
        }
        else if (clazz.isInterface())
        {
            for (Class<?> superIfc : clazz.getInterfaces())
            {
                doWithMethods(superIfc, mc, mf);
            }
        }
    }

    /**
     * 获取类leafClass及父类中所有的方法
     */
    public static Method[] getAllDeclaredMethods(Class<?> leafClass) throws IllegalArgumentException
    {
        final List<Method> methods = new ArrayList<Method>(32);
        doWithMethods(leafClass, new MethodCallback() {
            public void doWith(Method method)
            {
                methods.add(method);
            }
        });
        return methods.toArray(new Method[methods.size()]);
    }

    /**
     * Get the unique set of declared methods on the leaf class and all
     * superclasses. Leaf class methods are included first and while traversing
     * the superclass hierarchy any methods found with signatures matching a
     * method already included are filtered out.
     */
    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass) throws IllegalArgumentException
    {
        final List<Method> methods = new ArrayList<Method>(32);
        doWithMethods(leafClass, new MethodCallback() {
            public void doWith(Method method)
            {
                boolean knownSignature = false;
                Method methodBeingOverriddenWithCovariantReturnType = null;

                for (Method existingMethod : methods)
                {
                    if (method.getName().equals(existingMethod.getName())
                        && Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes()))
                    {
                        // is this a covariant return type situation?
                        if (existingMethod.getReturnType() != method.getReturnType()
                            && existingMethod.getReturnType().isAssignableFrom(method.getReturnType()))
                        {
                            methodBeingOverriddenWithCovariantReturnType = existingMethod;
                        }
                        else
                        {
                            knownSignature = true;
                        }
                        break;
                    }
                }
                if (methodBeingOverriddenWithCovariantReturnType != null)
                {
                    methods.remove(methodBeingOverriddenWithCovariantReturnType);
                }
                if (!knownSignature && !isCglibRenamedMethod(method))
                {
                    methods.add(method);
                }
            }
        });
        return methods.toArray(new Method[methods.size()]);
    }

    /***
     * 访问者模式：遍历clazz中所有字段Field,并执行fc逻辑
     * @param clazz
     * @param fc
     * @throws IllegalArgumentException
     */
    public static void doWithFields(Class<?> clazz, FieldCallback fc) throws IllegalArgumentException
    {
        doWithFields(clazz, fc, null);
    }

    /***
     * 访问者模式：遍历clazz中符合ff过滤规则的Field,并执行fc逻辑
     * @param clazz
     * @param fc
     * @param ff
     * @throws IllegalArgumentException
     */
    public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) throws IllegalArgumentException
    {
        // Keep backing up the inheritance hierarchy.
        Class<?> targetClass = clazz;
        do
        {
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields)
            {
                // Skip static and final fields.
                if (ff != null && !ff.matches(field))
                {
                    continue;
                }
                try
                {
                    fc.doWith(field);
                }
                catch (IllegalAccessException ex)
                {
                    throw new IllegalStateException("Shouldn't be illegal to access field '" + field.getName() + "': "
                        + ex);
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);
    }

    /**
     * Given the source object and the destination, which must be the same class
     * or a subclass, copy all fields, including inherited fields. Designed to
     * work on objects with public no-arg constructors.
     * @throws IllegalArgumentException if the arguments are incompatible
     */
    public static void shallowCopyFieldState(final Object src, final Object dest) throws IllegalArgumentException
    {
        if (src == null)
        {
            throw new IllegalArgumentException("Source for field copy cannot be null");
        }
        if (dest == null)
        {
            throw new IllegalArgumentException("Destination for field copy cannot be null");
        }
        if (!src.getClass().isAssignableFrom(dest.getClass()))
        {
            throw new IllegalArgumentException("Destination class [" + dest.getClass().getName()
                + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        }
        doWithFields(src.getClass(), new FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException
            {
                makeAccessible(field);
                Object srcValue = field.get(src);
                field.set(dest, srcValue);
            }
        }, COPYABLE_FIELDS);
    }

    /***
     * 功能描述：方法访问
     */
    public interface MethodCallback
    {
        void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
    }

    /***
     * 功能描述：方法过滤器
     */
    public interface MethodFilter
    {
        boolean matches(Method method);
    }

    /***
     * 功能描述：字段访问
     */
    public interface FieldCallback
    {
        void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
    }

    /***
     * 功能描述：字段过滤器
     */
    public interface FieldFilter
    {
        /** 过滤规则 */
        boolean matches(Field field);
    }

    /***
     * 过滤掉static和final修饰的字段
     */
    public static FieldFilter COPYABLE_FIELDS = new FieldFilter() {
        public boolean matches(Field field)
        {
            return !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()));
        }
    };

    /**
     * 返回非Bridge方法的过滤器
     */
    public static MethodFilter NON_BRIDGED_METHODS = new MethodFilter() {
        public boolean matches(Method method)
        {
            return !method.isBridge();
        }
    };

    /***
     * 返回非Bridge方法及非Object中声明的方法过滤器
     */
    public static MethodFilter USER_DECLARED_METHODS = new MethodFilter() {
        public boolean matches(Method method)
        {
            return (!method.isBridge() && method.getDeclaringClass() != Object.class);
        }
    };

}
