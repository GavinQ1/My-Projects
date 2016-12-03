#ifndef MERGE
#define MERGE
template <typename T>
void merge(T *arr, const size_t p, const size_t q, const size_t r, int(*cmp)(const T &i, const T &j)) {
    size_t i = p, j = q + 1, sz = r - p + 1;
    T aux[sz] = {0};
    for (size_t k = 0; k < sz; k++) {
        if (j > r) aux[k] = arr[i++];
        else if (i > q) aux[k] = arr[j++];
        else if ((*cmp)(arr[i], arr[j]) > 0) aux[k] = arr[j++];
        else aux[k] = arr[i++];
    }
    for (size_t k = 0; k < sz; k++)
        arr[p+k] = aux[k];
}

template <typename T>
void merge_sort(T *arr, const size_t p, const size_t r, int (*cmp)(const T &i, const T &j)) {
    if (p < r) {
        size_t q = (p + r) / 2;
        merge_sort(arr, p, q, cmp);
        merge_sort(arr, q+1, r, cmp);
        merge(arr, p, q, r, cmp);
    }
}

template <typename T>
void merge_sort(T *arr, const size_t sz, int (*cmp)(const T &i, const T &j)) {
    merge_sort(arr, 0, sz-1, cmp);
}

#endif // MERGE
