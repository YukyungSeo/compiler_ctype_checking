int sum(int x) {
	int sum = 0;
	int i = 0;
	while (i < x) {
	    ++i;
	    sum = sum + i;
	}
	return sum;
}

void main () {
    int x = 100;
	_print(sum(x));
}
