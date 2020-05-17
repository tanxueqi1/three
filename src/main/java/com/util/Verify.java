package com.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.Assert;

/**
 * @author wangy34 Tel:13145878905
 */
public class Verify {

    public static boolean flag = true;
    public static List<Error> errors = new ArrayList<Error>();

    public Verify() {
        // TODO Auto-generated constructor stub
    }

    // 比较是否相等，遍历集合中的每一个元素。
    public void iterateIsEquals(Collection<?> actualCol, String expectStr, String message) {
        Object[] obj = actualCol.toArray();
        int i = 0;
        System.out.println(message + " 遍历集合中的每一个元素，是否等于预期值：" + expectStr);
        for (Object s : obj) {
            i++;
            Boolean equals = s.toString().trim().equals(expectStr);
            if (equals) {
                System.out.println("第" + i + "个元素," + s + "==" + expectStr);
            } else {
                System.out.println("第" + i + "个元素," + s + "≠≠" + expectStr);
                try {
                    Assert.fail("第" + i + "个元素," + s + "≠≠" + expectStr);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    flag = false;
                }
            }
        }
    }

    // 右包含，实际值在预期范围内。⊆ ⊂ ⊇ ⊃ ∈＝≠
    public void iterateIsContainsR(Collection<?> actualCol, String expectStr, String message) {
        if (message.isEmpty()) {
            message = "检查(实际结果, 预期结果)是否包含:";
        }
        Object[] obj = actualCol.toArray();
        int i = 0;
        System.out.println(message + " 遍历集合中的每一个元素，是否包含在预期值[" + expectStr + "]");// expectStr="1,2,3"
        for (Object s : obj) {
            i++;
            Boolean contains = expectStr.trim().contains(s.toString());
            if (contains) {
                System.out.println("第" + i + "个元素,（" + s + "） ⊆ 预期值:{" + expectStr + "}");
            } else {
                System.out.println("第" + i + "个元素,（" + s + "） !⊆ 预期值:{" + expectStr + "}");
                // System.out.println("第"+i+"个元素,"+s+" !⊆ 预期值:{"+expectStr+"}");
                try {
                    Assert.fail("第" + i + "个元素,（" + s + "） !⊆ 预期值:{" + expectStr + "}");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    flag = false;
                }
            }
        }
    }

    // 实际值可能有多个字符串组成，只要包含预期值，结果则为true。
    public void iterateIsContains(Collection<?> actualCol, String expectStr, String message) {
        if (message.isEmpty()) {
            message = "检查(实际结果,预期结果)是否包含:";
        }
        Object[] obj = actualCol.toArray();
        int i = 0;
        System.out.println(message + " 遍历集合中的每一个元素,是否包含预期值[" + expectStr + "]");
        for (Object s : obj) {
            i++;
            Boolean contains = s.toString().contains(expectStr.trim());
            if (contains) {
                System.out.println("预期值:（" + expectStr + "）⊆第" + i + "个元素{" + s + "}");
            } else {
                System.out.println("预期值:（" + expectStr + "）!⊆第" + i + "个元素{" + s + "}");
                // System.out.println("预期值:" + expectStr+",!⊆第"+i+"个元素{"+s+"}");
                try {
                    Assert.fail("预期值:（" + expectStr + "）!⊆第" + i + "个元素{" + s + "}");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    flag = false;
                }
            }
        }
    }

    public void isContains(String actualStr, String expectStr, String message) {
        if (message.isEmpty()) {
            message = "检查(实际结果,预期结果)是否包含:";
        }
        actualStr = actualStr.trim();
        expectStr = expectStr.trim();
        Boolean contains = actualStr.contains(expectStr);
        if (contains) {
            System.out.println(message + "  预期值:（" + expectStr + "）⊆ 实际值:" + actualStr);
        } else {
            System.out.println(message + "  预期值:（" + expectStr + "）!⊆ 实际值:" + actualStr);
            try {
                Assert.fail(message + "  预期值:（" + expectStr + "）!⊆ 实际值:" + actualStr);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
    }

    // 调用Assert.assertEquals()方法，比较两个两个字符串。
    public void assertIsEquals(String actual, String expected, String message) {
        if (message.isEmpty()) {
            message = "检查(实际结果,预期结果)是否相等:";
        }

        actual = actual.trim();
        expected = expected.trim();

        try {
            Assert.assertEquals(actual, expected);
            System.out.println(message + ": " + actual + "==" + expected);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(message + ": " + actual + "≠≠" + expected);
            e.printStackTrace();
            flag = false;
        }
    }

    public void assertIsEquals(String actual, String expected) {
        assertIsEquals(actual, expected, "");
    }

    // 调用Assert.assertEquals()方法，比较两个集合是否相等。
    public void assertIsEquals(Collection<?> actual, Collection<?> expected, String message) {
        if (message.isEmpty()) {
            message = "检查(实际结果,预期结果)是否相等:";
        }

        try {
            Assert.assertEquals(actual, expected, message);
            System.out.println(message + ": " + actual + "==" + expected);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(message + ": " + actual + "≠≠" + expected);
            e.printStackTrace();
            flag = false;
        }
    }

    public void assertIsEquals(Collection<?> actual, Collection<?> expected) {
        assertIsEquals(actual, expected, "");
    }

    // 判断真假
    public void assertTrue(Boolean checkResult) {
        assertTrue(checkResult, "");
    }

    public void assertTrue(Boolean isTrue, String Message) {
        Boolean actual = isTrue;
        Boolean expected = true;
        try {
            Assert.assertEquals(actual, expected, Message);
            System.out.println(Message + " 检查(实际结果是否为True),返回的布尔值为真True");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(Message + "检查(实际结果是否为True),返回的布尔值为假False");
            e.printStackTrace();
            flag = false;
        }
    }

    // 标记结果为失败
    public void assertFail() throws Throwable {
        flag = false;
        Throwable throwable = new Throwable("执行接口失败了！详细信息,请查看日志apilog.log");
        throw throwable;
    }

}
