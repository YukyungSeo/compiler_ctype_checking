package listener.main;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class Test {
    public Test() {
    }

    public static void multi(int a[], int b){
        System.out.println(a[0] + a[1] + b);
    }

    public static void main(String[] var0) {
        int arr[] = new int[5];
        arr[0] = 100;
        arr[1] = 200;
        multi(arr, 1);
    }
}