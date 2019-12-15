package listener.main;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class Test {
    public Test() {
    }

    public static int[] init(int[] arr){
        arr[2] = 200;
        arr[3] = 300;
        return arr;
    }

    public static void main(String[] var0) {
        int arr[] = new int[5];
        int[] b;
        arr[0] = 600;
        arr[1] = 100;
        b = init(arr);
        System.out.println(b[3]);
    }
}