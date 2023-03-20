
pub struct Solution {

}
impl Solution {
    pub fn move_zeroes(nums: &mut Vec<i32>) {
        let mut j = 0;
        for i in 0..nums.len() {
            if nums[i] != 0 {
                nums[j] = nums[i];
                if i != j {
                    nums[i] = 0;
                }
                j += 1;
            }
        }
    }
}
