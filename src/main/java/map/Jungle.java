package map;

public interface Jungle {

    private int calculateWidth(double jungleRatio, int width)
    {
        return (int) Math.floor(width*jungleRatio);
    }

    private int calculateHeight(double jungleRatio, int height)
    {
        return (int) Math.floor(height*jungleRatio);
    }

    public default Vector2d jungleLowerLeftCorner(int width, int height, double jungleRatio, Vector2d mapCenter)
    {
        int jungleWidth = calculateWidth(jungleRatio, width);
        int jungleHeight = calculateHeight(jungleRatio, height);

        return new Vector2d((int)Math.floor(mapCenter.x - jungleWidth/2), (int)Math.floor(mapCenter.y - jungleHeight/2));
    }

    public default Vector2d jungleUpperRightCorner(int width, int height, double jungleRatio, Vector2d mapCenter)
    {
        int jungleWidth = calculateWidth(jungleRatio, width);
        int jungleHeight = calculateHeight(jungleRatio, height);

        return new Vector2d((int)Math.floor(mapCenter.x + jungleWidth/2), (int)Math.floor(mapCenter.y + jungleHeight/2));
    }
}
