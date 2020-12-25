package nl.tvandijk.aoc.day14;

class TreeNode {
    final TreeNode edge0;
    final TreeNode edge1;
    final long value;

    public TreeNode(TreeNode edge0, TreeNode edge1) {
        this.edge0 = edge0;
        this.edge1 = edge1;
        this.value = 0;
    }

    public TreeNode(long value) {
        this.edge0 = this.edge1 = null;
        this.value = value;
    }

    public long computeValue() {
        if (edge0 == null && edge1 == null) return value;
        else if (edge0 == null) return edge1.computeValue();
        else if (edge1 == null) return edge0.computeValue();
        else if (edge0 == edge1) return edge0.computeValue() * 2;
        else return edge0.computeValue() + edge1.computeValue();
    }

    public static TreeNode set(TreeNode cur, long mem, String mask, long val) {
        if (mask.length() == 0) return new TreeNode(val);
        long M = 1L << (mask.length() - 1);

        switch (mask.charAt(0)) {
            case '1':
                if (cur == null) return new TreeNode(null, set(null, mem, mask.substring(1), val));
                else return new TreeNode(cur.edge0, set(cur.edge1, mem, mask.substring(1), val));
            case '0':
                if ((mem & M) == 0) {
                    if (cur == null) return new TreeNode(set(null, mem, mask.substring(1), val), null);
                    else return new TreeNode(set(cur.edge0, mem, mask.substring(1), val), cur.edge1);
                } else {
                    if (cur == null) return new TreeNode(null, set(null, mem, mask.substring(1), val));
                    else return new TreeNode(cur.edge0, set(cur.edge1, mem, mask.substring(1), val));
                }
            case 'X':
                if (cur == null) {
                    var res = set(null, mem, mask.substring(1), val);
                    return new TreeNode(res, res);
                } else {
                    if (cur.edge0 == cur.edge1) {
                        var res = set(cur.edge0, mem, mask.substring(1), val);
                        return new TreeNode(res, res);
                    } else {
                        return new TreeNode(set(cur.edge0, mem, mask.substring(1), val), set(cur.edge1, mem, mask.substring(1), val));
                    }
                }
            default:
                throw new RuntimeException("logic error");
        }
    }
}
