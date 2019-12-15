int[] init(int[] arr) {
    arr[2] = 200;
    arr[3] = 300;
    return arr;
}

void main () {
    int a[5];
    int[] b;
    a[0] = 600;
    a[1] = 100;
    b = init(a);
    _print(b[3]);
}