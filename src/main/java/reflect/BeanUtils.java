package reflect;

import java.lang.reflect.Field;

/**
 * 采⽤反射机制来实现⼀个⼯具BeanUtils，可以将⼀个对象属性相同的值赋值给另⼀个对象
 */
public class BeanUtils {


    public static void convertor(Object originObj, Object targetObj) throws
            Throwable{
        // 第⼀步，获得class对象
        Class orginClazz = originObj.getClass();
        Class targetClazz = targetObj.getClass();
        // 第⼆步，获得Field
        Field[] orginFields = orginClazz.getDeclaredFields();
        Field[] targetFields = targetClazz.getDeclaredFields();
        // 第三步：赋值呗
        for (Field originField : orginFields) {
            for (Field targetField : targetFields) {
                // 两个对象属性名相同，才会赋值【应用场景：仅想使用该类，但又担心该类被修改】
                if (originField.getName().equals(targetField.getName())) {
                    originField.setAccessible(true);
                    targetField.setAccessible(true);
                    targetField.set(targetObj, originField.get(originObj));
                }
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        // Service层返回的
        Person person = new Person("muse", 10, (byte)1, true);
        // 需要返回给前段实体对象
        Person1 person1 = new Person1();
        BeanUtils.convertor(person, person1);
        System.out.println("person, person1" + person + person1);
    }
}
