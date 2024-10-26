#include <stdio.h>
#include <math.h>


void bin(int n, char *result){
    int len = floor(log(n)/log(2)) + 1;
    int i = len-1;

    while(n != 0){
        result[i] = n % 2 + 48; // + 48 converts int to str (ASCII)
        i--;
        n /= 2;
    }
    
    result[len] = '\0';
    //return result;
}

int main() {
    int num;
    printf("Integer: ");
    scanf("%d", &num);
    
    int len = floor(log(num)/log(2)) + 1;//length of the binary equivalent
    char result[len];

    bin(num, result);
    printf("Binary = %s", result);
    return 0;
}

