#ifndef QUICK
#define QUICK
#include ".\sort_helper.h"

template <typename T>
int parti(T *arr, const int p, const int r, int(*cmp)(const T&, const T&)) {
    T x = arr[r];
    int i = p - 1;
    for (int j = p; j < r; j++)
        if ((*cmp)(x, arr[j])) exch(&arr[++i], &arr[j]);
    exch(&arr[++i], &arr[r]);
    return i;
}

template <typename T>
void quick_sort(T *arr, const int p, const int r, int(*cmp)(const T&, const T&)) {
    if (p < r) {
        int q = parti(arr, p, r, cmp);
        quick_sort(arr, p, q-1, cmp);
        quick_sort(arr, q+1, r, cmp);
    }
}

template <typename T>
void quick_sort(T *arr, const size_t sz, int(*cmp)(const T&, const T&)) {
    quick_sort(arr, 0, sz-1, cmp);
}

#endif // QUICK
