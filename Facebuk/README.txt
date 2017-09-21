abstract class Profile
Since all objects in the Facebuk system have at least an image
and a name, we made a class called "Profile" which contains the
two variables and unites the different Facebuk objects which are
composed of (among other thing) these two variables. This 
reduces the redundancy of creating name and image variables for 
every object created. Friend, Possession, and Moment all extend 
Profile.

abstract class Friend extends Profile
Since friends can either be a Pet or a Person, we made a class 
called "Friend" which both Pet and Person extend. This highlights
the common characteristics that both Pet and Person can have a
list of Friend and a list of Moment for each Pet or Person. Friend
extends the Profile class. However, the two cannot be combined 
because Moments and Possessions extend Profile but do not have
list of Friend and list of Moment, like the Pet and Person objects.
The Friend class also implements setFriends(ArrayList friends) 
and setMoments(ArrayList moments). Therefore, the Friend parent
class reduces the redundancy of setFriends and setMoments methods
created in bother the Pet and Person classes.

