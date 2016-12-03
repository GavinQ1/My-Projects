#ifndef COUNTING
#define COUNTING
#include<iostream>
using namespace std;

template <typename T>
T max_elt(T arr[], const size_t sz) {
    T max = 0;
    for (int i = 0; i < sz; i++) {
        if (arr[i] > max)
            max = arr[i];
    }
    return max;
}

template <typename T>
void counting_sort(T arr[], T b[], const size_t sz) {
    int k = max_elt(arr, sz);
    int c[k+1] = {0};

    for (int i = 0; i < sz; i++) c[arr[i]]++;

    for (int i = 1; i < k+1; i++) {
        c[i] = c[i] + c[i-1];
    }

    for (int i = sz-1; i > -1; i--) {
        b[c[arr[i]]-1] = arr[i];
        c[arr[i]]--;
    }
}

template <typename T>
void counting_sort(T arr[], const size_t sz) {
    T copy[sz];
    for (int i = 0; i < sz; i++) {
        copy[i] = arr[i];
    }
    counting_sort(copy, arr, sz);
}
#endif // COUNTING
