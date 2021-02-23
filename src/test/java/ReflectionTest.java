import org.junit.Test;
import reflect.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionTest {

    @Test
    public void test() throws ClassNotFoundException {
        // 方式一  类.class
        Class personClazz = Person.class;

        // 方式二  实例.getClass()
        Person person = new Person();
        Class personClazz1 = person.getClass();

        // 方式三  Class.forName("类的全路径")
        Class personClazz2 = Class.forName("reflect.Person");

        // true true Class文件只有一份，所以是相同的
        System.out.println(personClazz == personClazz1);
        System.out.println(personClazz == personClazz2);

    }

    /**
     * //无参数 Spring中声明一个无参数的bean，可通过反射获取具体对象
     * <bean id="person" class="reflect.Person></bean>
     *
     */
    @Test
    public void test2() throws Exception {
        // 第一步：获取Class
        Class personClazz = Person.class;
        // 第二步：获得构造方法
        Constructor<Person> constructor = personClazz.getConstructor();
        // 第三步：创建对象
        Person person = constructor.newInstance();
        person.setName("lch");
        System.out.println(person.getName());

    }

    /**
     * //有参数 Spring中声明一个有参数的bean，可通过传入构造函数中的参数类型、实参，通过反射获取具体对象
     * <bean id="person" class="reflect.Person>
     *     <constructor-arg index="0" type="java.lang.String" value="lch"/>
     * </bean>
     *
     */
    @Test
    public void test3() throws Exception {
        // 第一步：获取Class
        Class personClazz = Person.class;
        // 第二步：获得构造方法
        Constructor<Person> constructor = personClazz.getConstructor(String.class, Integer.class, Byte.class, Boolean.class);
        // 第三步：创建对象
        Person person = constructor.newInstance("lch", 28, (byte)81,true);

        System.out.println(person);
    }

    @Test
    public void test4() throws Exception {
        // 第一步：获取Class
        Class personClazz = Person.class;
        // 第二步：获得构造方法
        Constructor<Person> constructor = personClazz.getConstructor();
        // 第三步：通过class对象，获得Field对象
        Field nameField = personClazz.getField("name");
        System.out.println(nameField);
    }

    /**
     *
     * public属性
     */
    @Test
    public void test5() throws Exception {
        // 第一步：获取Class
        Class personClazz = Person.class;
        // 第二步：获得构造方法
        Constructor<Person> constructor = personClazz.getConstructor();
        Person person = constructor.newInstance();
        person.setName("sunny");
        // 第三步：通过class对象，获得Field对象
        Field nameField = personClazz.getField("name");
        // 第四步：操作Field，获得属性值 （传入具体对象获取属性值）
        String name = String.valueOf(nameField.get(person));
        System.out.println(name);
    }

    /**
     *
     * private属性
     * 1.不能调用personClazz.getField("sex")，否则报错：java.lang.NoSuchFieldException: sex
     * 2.调用getDeclaredField后，必须声明sexField.setAccessible(true);放开权限；
     */
    @Test
    public void test6() throws Exception {
        // 第一步：获取Class
        Class personClazz = Person.class;
        // 第二步：获得构造方法
        Constructor<Person> constructor = personClazz.getConstructor();
        Person person = constructor.newInstance();
        // 第三步：通过class对象，获得Field对象
        Field sexField = personClazz.getDeclaredField("sex");
        // 为Field设置访问权限，才能获取private属性
        sexField.setAccessible(true);
        // 第四步：操作Field，获得属性值 （传入具体对象获取属性值）
        String sex = String.valueOf(sexField.get(person));
        System.out.println(sex);
    }
}
