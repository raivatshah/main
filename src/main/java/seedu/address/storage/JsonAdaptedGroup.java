package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Name;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.Time;

/**
 * Jackson-friendly version of {@link Group}.
 */
public class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Groups's %s field is missing!";

    private final String name;
    private final String groupId;
    private final String timeSpent;
    private final List<String> memberIDs = new ArrayList<>();
    private final List<String> eventIDs = new ArrayList<>();
    private final List<String> places = new ArrayList<>();
    private final List<String> activities = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("name") String name, @JsonProperty("groupId") String groupId,
                            @JsonProperty("timeSpent") String timeSpent,
                            @JsonProperty("memberIDs") List<String> memberIDs,
                            @JsonProperty("eventIDs") List<String> eventIDs,
                            @JsonProperty("places") List<String> places,
                            @JsonProperty("activities") List<String> activities) {
        this.name = name;
        this.groupId = groupId;
        this.timeSpent = timeSpent;

        if (memberIDs != null) {
            this.memberIDs.addAll(memberIDs);
        }

        if (eventIDs != null) {
            this.eventIDs.addAll(eventIDs);
        }

        if (!places.isEmpty()) {
            this.places.addAll(places);
        }
        if (!activities.isEmpty()) {
            this.activities.addAll(activities);
        }
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        name = source.getName().fullName;
        groupId = Integer.toString(source.getGroupId());
        timeSpent = source.getTimeSpent().toString();

        List<Integer> members = source.getMembers();
        for (int i = 0; i < members.size(); i++) {
            memberIDs.add(Integer.toString(members.get(i)));
        }

        List<Integer> events = source.getEvents();
        for (int i = 0; i < events.size(); i++) {
            eventIDs.add(Integer.toString(events.get(i)));
        }

        places.addAll(source.getPlaceList().placeList);
        activities.addAll(source.getActivityList().activityList);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               person.
     */

    public Group toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        //%TODO: Add more checks as group class and info evolve

        final PlaceList modelPlaceList = new PlaceList(places);

        final ActivityList modelActivityList = new ActivityList(activities);

        Group group = new Group(new Name(name), modelPlaceList, modelActivityList);
        String[] times = timeSpent.split(" ");
        String hours = times[0].substring(0, times[0].length() - 1);
        String minutes = times[1].substring(0, times[1].length() - 1);
        Time time = new Time(Integer.valueOf(hours), Integer.valueOf(minutes));
        group.setTimeSpent(time);

        ArrayList<Integer> members = new ArrayList<>();
        for (int i = 0; i < memberIDs.size(); i++) {
            members.add(Integer.valueOf(memberIDs.get(i)));
        }
        group.setMemberIDs(members);

        ArrayList<Integer> events = new ArrayList<>();
        for (int i = 0; i < eventIDs.size(); i++) {
            events.add(Integer.valueOf(eventIDs.get(i)));
        }

        group.setEventIDs(events);

        return group;
    }


}
