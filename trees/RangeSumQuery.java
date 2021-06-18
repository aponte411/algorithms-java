class NumArray {

    int[] tree;
    int size;

    public static void main(String[] args){
        int[] nums = [1, 3, 5];
        int index = 1, val = 2;
        int left = 0, right = 2;
        NumArray arr = new NumArray(nums);
        arr.update(index, val);
        int result = arr.sumRange(left, right);
        System.out.println(result);
    }

    public NumArray(int[] nums) {
        if (nums.length > 0) {
            size = nums.length;
            tree = new int[size * 2];
            buildTree(nums);
        }

    }

    public void buildTree(int[] nums) {
        // build segment tree with parents as aggregates
        // with the children holding the sum for the range
        // left (i-> i+j/2), right (i+j/2 + 1 -> j)

        for (int i = size, j = 0; i < 2 * size; i++, j++){
            tree[i] = nums[j];
        }
        for (int i = size - 1; i > 0; i--){
            tree[i] = nums[2*i] + nums[2*i + 1];
        }

    }

    public void update(int index, int val) {
        index += size;
        tree[index] = val;
        while (index > 0) {
            int left = index;
            int right = index;
            if (index % 2 == 0) {
                right = index + 1;
            } else {
                left = index - 1;
            }
            // Update children then parents
            tree[index/2] = tree[left] + tree[right];
            index /= 2;
        }
    }

    public int sumRange(int left, int right) {
        left += size;
        right += size; // get us the leaves with value left+ right
        int sum = 0;
        while (left <= right){
            if ((left % 2 == 1)){
                sum += tree[left];
                left++;
            }
            if ((right % 2 == 0)) {
                sum += tree[right];
                right--;
            }
            left /= 2;
            right /= 2;
        }
        return sum;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */
