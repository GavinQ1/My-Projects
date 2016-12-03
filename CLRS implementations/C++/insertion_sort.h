#ifndef INSERTION
#define INSERTION
template <typename T>
void insertion_sort(T *arr,
                    const size_t sz,
                    int (*cmp)(const T &i, const T &j)) {
    for (int i = 1; i < sz; i++) {
        T key = arr[i];
        int j = i - 1;
        while (j >= 0 && (*cmp)(arr[j], key) > 0) {
            arr[j+1] = arr[j--];
        }
        arr[j+1] = key;
    }

}

#endif // INSERTION
