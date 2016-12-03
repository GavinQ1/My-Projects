#ifndef HEAP
#define HEAP

int parent(const int i) {
    return (i / 2);
}

int left(const int i) {
    return (2 * i);
}

int right(const int i) {
    return (2 * i + 1);
}

template <typename T>
void heapify(T *arr,
             const int i,
             const int sz,
             int(*cmp)(const T &i, const T &j)) {

    int key_index, l = left(i), r = right(i);

    key_index = (l < sz && (*cmp)(arr[l], arr[i])) ? l : i;
    key_index = (r < sz && (*cmp)(arr[r], arr[key_index])) ? r : key_index;

    if (key_index != i) {
        exch(&arr[i], &arr[key_index]);
        heapify(arr, key_index, sz, cmp);
    }
}

template <typename T>
void build_heap(T *arr, const int sz, int(*cmp)(const T &i, const T &j)) {
    for (int i = (sz-1)/2; i > -1; i--)
        heapify(arr, i, sz, cmp);
}

template <typename T>
void heap_sort(T *arr, const int sz, int(*cmp)(const T &i, const T &j)) {
    build_heap(arr, sz, cmp);
    int hsz = sz;
    for (int i = sz - 1; i > -1; i--) {
        exch(&arr[0], &arr[i]);
        heapify(arr, 0, --hsz, cmp);
    }
}

#endif // HEAP
