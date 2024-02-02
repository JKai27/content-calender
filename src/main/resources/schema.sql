CREATE TABLE IF NOT EXISTS content (
    id INTEGER AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    desc TEXT,
    status VARCHAR(20) NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    date_updated TIMESTAMP,
    url VARCHAR(255),
    PRIMARY KEY(id)
);

INSERT INTO content (title, desc, status, content_type, date_created)
VALUES
    ('German Grammar Basics', 'Introduction to basic grammar in German language', 'IDEA', 'ARTICLE', CURRENT_TIMESTAMP),
    ('Advanced German Vocabulary', 'Exploring advanced vocabulary in German language', 'IN_PROGRESS', 'ARTICLE', CURRENT_TIMESTAMP),
    ('German Verb Conjugation', 'Understanding verb conjugation in German language', 'IDEA', 'VIDEO', CURRENT_TIMESTAMP),
    ('Mastering German Pronunciation', 'Tips for mastering German pronunciation', 'COMPLETED', 'VIDEO', CURRENT_TIMESTAMP),
    ('German Grammar Course', 'Comprehensive course on German grammar', 'IN_PROGRESS', 'COURSE', CURRENT_TIMESTAMP),
    ('Exploring German Culture', 'Dive into the cultural aspects of Germany', 'IDEA', 'ARTICLE', CURRENT_TIMESTAMP),
    ('Effective German Communication', 'Strategies for effective communication in German', 'COMPLETED', 'COURSE', CURRENT_TIMESTAMP),
    ('German Language Conference Highlights', 'Highlights from the recent German language conference', 'PUBLISHED', 'CONFERENCE_TALK', CURRENT_TIMESTAMP),
    ('Interactive German Learning', 'Engaging methods for learning German interactively', 'IDEA', 'COURSE', CURRENT_TIMESTAMP),
    ('Understanding German Literature', 'Exploring classic and modern German literature', 'IN_PROGRESS', 'ARTICLE', CURRENT_TIMESTAMP);
