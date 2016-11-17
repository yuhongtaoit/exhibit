package edu.zstu.exhibit.domain;

public class Directory {

    public Directory() {}

    public Directory(String directoryName, int directoryType) {
        this.directoryName = directoryName;
        this.directoryType = directoryType;
    }

    private int Id;

    private String directoryName;

    private int directoryType;

    @Override
    public String toString() {
    	
        return "Directory{" +
                "Id=" + Id +
                ", directoryName='" + directoryName + '\'' +
                ", directoryType=" + directoryType +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public int getDirectoryType() {
        return directoryType;
    }

    public void setDirectoryType(int directoryType) {
        this.directoryType = directoryType;
    }
}