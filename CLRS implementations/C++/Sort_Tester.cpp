#include<iostream>
#include ".\sort_helper.h"
#include ".\insertion_sort.h"
#include ".\merge_sort.h"
#include ".\heap_sort.h"
#include ".\quick_sort.h"
#include ".\counting_sort.h"
using namespace std;

int main() {
    int arr[] = {8, 0, 3, 4, 10, 3, 3, 3, 100};
    const size_t sz = end(arr) - begin(arr);

    cout << "Before: ";
    print(arr, sz);

    //insertion_sort(arr, sz, increasing_order);
    //merge_sort(arr, sz, increasing_order);
    //heap_sort(arr, sz, increasing_order);
    //quick_sort(arr, sz, decreasing_order);
    counting_sort(arr, sz);

    cout << "\nAfter:  ";
    print(arr, sz);

}
