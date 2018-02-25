# MeetupMarker [![Travis branch](https://img.shields.io/travis/rust-lang/rust/master.svg)]() [![AUR](https://img.shields.io/badge/License-GPL----3-green.svg)]() [![CocoaPods](https://img.shields.io/cocoapods/metrics/doc-percent/AFNetworking.svg)]

MeetupMarker is designed to bring people together, whether it be in Urbana-Champaign or the suburbs of Chicago. The app allows for people to make friends and meet by joining up for various sports (and other social activities) in the future.

## Usage
#### RESTful API
Base URL: https://meetupmarker.appspot.com
* /register -> POST with param(userId, firstname, lastname, bio, picUrl) -> Puts user data into the DB
* /is_registered -> GET with arg(userId) -> Checks whether user is already registered
* /get_user -> GET with arg(user_id) -> Returns the data associated with the user
* /register_event -> POST with param(user_id, lat, long, num_people, basketball, soccer, work_out, tennis, racquetball,       
                                     hockey, users_joined) -> Puts event data into the DB
* /event_registered -> GET with arg(user_id) -> Checks whether a user already has an event
* /register_for_event -> POST with param(host_id, user_id) -> Adds user to an event hosted by another user (if possible)
* /get_event_by_host -> GET with arg(user_id) -> Returns event that a user is hosting
* /get_event_by_joined -> GET with arg(user_id) -> Returns event that a user has joined


## Why is it called 'MeetupMarker'?
The inspiration for our name came from the idea of social meetups and marking them on a map.

## [Contributors] (CONTRIBUTORS.md)
1. Osama Esmail
2. Danish Arsalan
3. Ammar Hoque

## Contributing to this project
We love contributors. We are open source and every contribution helps. Please read [CONTRIBUTE.md](CONTRIBUTE.md) for how to contribute to our project.

## Licensing
This project is protected under GNU General Public License 3.

Copyright (C) 2007 Free Software Foundation, Inc. http://fsf.org/ Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.

Please read [License.md](LICENSE.md)

## Roadmap
* Set start and end times for events
* Filter events based off of user preference
* Set profile filled-out requirements
* Add more event choices
* Delete events before they occur
* Roster feature to allow user to see who joined
* Group chat feature for users part of an event
