class Solution {
    private int[] head, to, next;
    private int edgeCount, n;
    private int[] parent, depth, size, heavy;
    private int[] chainHead, position;
    private int currentPosition;
    private int[] segmentTree;
    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {
        this.n = n;

        head = new int[n];
        Arrays.fill(head, -1);

        to = new int[2 * edges.length];
        next = new int[2 * edges.length];
        edgeCount = 0;

        for (int[] edge : edges) {
            addEdge(edge[0], edge[1]);
            addEdge(edge[1], edge[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];

        Arrays.fill(parent, -1);
        Arrays.fill(heavy, -1);
        Arrays.fill(size, 1);

        // Iterative DFS
        int[] order = new int[n];
        int[] stack = new int[n];
        int top = 0, count = 0;

        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];
            order[count++] = u;

            for (int e = head[u]; e != -1; e = next[e]) {
                int v = to[e];

                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        // Subtree sizes and heavy children
        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            int p = parent[u];

            size[p] += size[u];

            if (heavy[p] == -1 ||
                    size[u] > size[heavy[p]]) {
                heavy[p] = u;
            }
        }

        // Heavy-Light Decomposition
        chainHead = new int[n];
        position = new int[n];
        currentPosition = 0;

        int[] startStack = new int[n];
        int[] headStack = new int[n];
        int chainTop = 0;

        startStack[chainTop] = 0;
        headStack[chainTop++] = 0;

        while (chainTop > 0) {
            chainTop--;
            int start = startStack[chainTop];
            int h = headStack[chainTop];

            for (int u = start; u != -1; u = heavy[u]) {
                chainHead[u] = h;
                position[u] = currentPosition++;

                for (int e = head[u]; e != -1; e = next[e]) {
                    int v = to[e];

                    if (parent[v] == u && v != heavy[u]) {
                        startStack[chainTop] = v;
                        headStack[chainTop++] = v;
                    }
                }
            }
        }

        // Build segment tree
        char[] chars = s.toCharArray();
        int[] base = new int[n];

        for (int u = 0; u < n; u++) {
            base[position[u]] = 1 << (chars[u] - 'a');
        }

        segmentTree = new int[4 * n];
        build(1, 0, n - 1, base);

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                chars[u] = c;

                update(
                    1, 0, n - 1,
                    position[u],
                    1 << (c - 'a')
                );
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int mask = pathMask(u, v);

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private void addEdge(int u, int v) {
        to[edgeCount] = v;
        next[edgeCount] = head[u];
        head[u] = edgeCount++;
    }

    private int pathMask(int u, int v) {
        int mask = 0;

        while (chainHead[u] != chainHead[v]) {
            if (depth[chainHead[u]] < depth[chainHead[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }

            mask ^= query(
                1, 0, n - 1,
                position[chainHead[u]],
                position[u]
            );

            u = parent[chainHead[u]];
        }

        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        mask ^= query(
            1, 0, n - 1,
            position[u],
            position[v]
        );

        return mask;
    }
    private void build(int node, int left, int right, int[] base) {
        if (left == right) {
            segmentTree[node] = base[left];
            return;
        }
        int mid = (left + right) / 2;
        build(node * 2, left, mid, base);
        build(node * 2 + 1, mid + 1, right, base);
        segmentTree[node] =
            segmentTree[node * 2] ^
            segmentTree[node * 2 + 1];
    }
    private void update(
            int node, int left, int right,
            int index, int value) {

        if (left == right) {
            segmentTree[node] = value;
            return;
        }
        int mid = (left + right) / 2;
        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }
        segmentTree[node] =
            segmentTree[node * 2] ^
            segmentTree[node * 2 + 1];
    }
    private int query(
            int node, int left, int right,
            int queryLeft, int queryRight) {
        if (queryLeft <= left && right <= queryRight) {
            return segmentTree[node];
        }
        int mid = (left + right) / 2;
        int mask = 0;
        if (queryLeft <= mid) {
            mask ^= query(
                node * 2, left, mid,
                queryLeft, queryRight
            );
        }
        if (queryRight > mid) {
            mask ^= query(
                node * 2 + 1, mid + 1, right,
                queryLeft, queryRight
            );
        }
        return mask;
    }
}