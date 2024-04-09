package lsp.practice;

public class MergeSort {
    public static void main(String[] args) {

    }

    public static void mergeSort(int[] nums, int left, int right) {
        int mid = (right - left) / 2 + left;
        if (left < right) {
            mergeSort(nums, left, mid);
            mergeSort(nums, mid + 1, right);
            merge(nums, left, mid, right);
        }
    }

    public static void merge(int[] nums, int left, int mid, int right) {
        int i = left;
        int j = right;
        int[] temp = new int[right - left + 1];
        int index = 0;
        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                temp[index++] = nums[i++];
            } else {
                temp[index++] = nums[j++];
            }
        }
        while (i <= mid) {
            temp[index++] = nums[i++];
        }
        while (j <= right) {
            temp[index++] = nums[j++];
        }
        for (int m = 0; m < temp.length; m++) {
            nums[left + m] = temp[m];
        }
    }
}
