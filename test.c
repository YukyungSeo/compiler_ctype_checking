int[] change(float[] a) {
    int b[5];
    b[0] = a[0];
    b[1] = a[1];
    return b;
}

void main () {
    float arr[5];
    arr[0] = 600;
    arr[1] = 100;
    _print(change(arr));
}