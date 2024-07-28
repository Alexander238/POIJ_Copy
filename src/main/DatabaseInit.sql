CREATE DATABASE IF NOT EXISTS fotomanager;
USE fotomanager;

/* TAGS */
CREATE TABLE TagTypes (
    id int not null AUTO_INCREMENT,
    type varchar(100) not null,
    PRIMARY KEY (id)
);

CREATE TABLE Tags (
    id int not null AUTO_INCREMENT,
    name varchar(100) not null,
    tagType int not null,
    color varchar(100),
    FOREIGN KEY (tagType) REFERENCES TagTypes(id),
    PRIMARY KEY (id)
);

CREATE TABLE MediaTags (
    mediaID int not null,
    tagID int not null,
    FOREIGN KEY (mediaID) REFERENCES Media(id),
    FOREIGN KEY (tagID) REFERENCES Tags(id),
    PRIMARY KEY (mediaID, tagID)
);

CREATE TABLE MediaTypes (
    id int not null AUTO_INCREMENT,
    type varchar(100) not null,
    PRIMARY KEY (id)
);


CREATE TABLE Media (
    id int not null AUTO_INCREMENT,
    name varchar(100),
    path varchar(100) not null,
    date date,
    description varchar(1000),
    isPrivate boolean DEFAULT FALSE,
    mediaType int not null,
    resolution varchar(100),
    orientation varchar(100),
    rating int CONSTRAINT chk_rating CHECK (rating >= 0 AND rating <= 5) DEFAULT 0,
    FOREIGN KEY (mediaType) REFERENCES MediaTypes(id),
    FOREIGN KEY (tagListID) REFERENCES TagList(id),
    PRIMARY KEY (id)
);

CREATE TABLE DataStructureType (
    id int not null AUTO_INCREMENT,
    type varchar(100) not null,
    PRIMARY KEY (id)
);

CREATE TABLE DataStructure (
    id int not null AUTO_INCREMENT,
    name varchar(100) not null,
    type int not null,
    folderDate date,
    folderEvent varchar(100),
    FOREIGN KEY (type) REFERENCES DataStructureType(id),
    PRIMARY KEY (id)
);

CREATE TABLE DataStructureMedia(
    dataStructureID int not null,
    mediaID int not null,
    FOREIGN KEY (dataStructureID) REFERENCES DataStructure(id),
    FOREIGN KEY (mediaID) REFERENCES Media(id),
    PRIMARY KEY (dataStructureID, mediaID)
);
)