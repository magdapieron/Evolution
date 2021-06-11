package objects;

public class TrackedAnimal {

    private Animal animal;
    private int numberOfNewChildren;
    private int numberOfNewAncestor;

    public void newTrackedAnimal(Animal animal)
    {
        this.animal = animal;
        animal.setTracked(true);
        this.numberOfNewChildren = 0;
        this.numberOfNewAncestor = 0;
    }

    public void newChildren()
    {
        this.numberOfNewChildren++;
    }

    public void newAncestor()
    {
        this.numberOfNewAncestor++;
    }

    public Animal getAnimal() {
        return animal;
    }

    public int getNumberOfNewChildren() {
        return numberOfNewChildren;
    }

    public int getNumberOfNewAncestor() {
        return numberOfNewAncestor;
    }
}
