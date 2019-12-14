int sum(int x) {
    int result = 0;
    int i = 0;
    while (i < x) {
        ++i;
        result = result + i;
    }

    return result;
}

void main () {
    float x = 4.5;
    _print(sum(x));
}