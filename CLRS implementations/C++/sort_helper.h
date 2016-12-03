#ifndef sort_helper
#define sort_helper
#include<iostream>

template <typename T>
void print(const T *arr, const size_t sz) {
    for (int i = 0; i < sz; i++) {
        std::cout << arr[i] << " ";
    }
    std::cout << std::endl;
}

template <typename T>
int decreasing_order(const T &i, const T &j) {
    if (i < j) return 1;
    return 0;
}

template <typename T>
int increasing_order(const T &i, const T &j) {
    if (i > j) return 1;
    return 0;
}

template <typename T>
void exch(T *i, T *j) {
    T temp = *i;
    *i = *j;
    *j = temp;
}
#endif // sort_helper

