package org.scarike.minecraft.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 私聊
 * test1
 * {
 *     "post_type": "message",
 *     "message_type": "private",
 *     "time": 1662976511,
 *     "self_id": 1022269770,
 *     "sub_type": "friend",
 *     "user_id": 1844246823,
 *     "target_id": 1022269770,
 *     "message": "test1",
 *     "raw_message": "test1",
 *     "font": 0,
 *     "sender": {
 *         "age": 0,
 *         "nickname": "Sc4r1ke",
 *         "sex": "unknown",
 *         "user_id": 1844246823
 *     },
 *     "message_id": -1794592461
 * }
 */

/**
 * 私聊
 * @將驰 \滑稽 test2
 * {
 *     "post_type": "message",
 *     "message_type": "private",
 *     "time": 1662976595,
 *     "self_id": 1022269770,
 *     "sub_type": "friend",
 *     "sender": {
 *         "age": 0,
 *         "nickname": "Sc4r1ke",
 *         "sex": "unknown",
 *         "user_id": 1844246823
 *     },
 *     "message_id": -2100819904,
 *     "user_id": 1844246823,
 *     "target_id": 1022269770,
 *     "message": "@将驰 [CQ:face,id=178] test2",
 *     "raw_message": "@将驰 [CQ:face,id=178] test2",
 *     "font": 0
 * }
 */

/**
 * 群聊
 * test3
 * {
 *     "post_type": "message",
 *     "message_type": "group",
 *     "time": 1662976884,
 *     "self_id": 1022269770,
 *     "sub_type": "normal",
 *     "anonymous": null,
 *     "font": 0,
 *     "group_id": 902338238,
 *     "message": "test3",
 *     "message_seq": 50,
 *     "raw_message": "test3",
 *     "sender": {
 *         "age": 0,
 *         "area": "",
 *         "card": "scarike",
 *         "level": "",
 *         "nickname": "Sc4r1ke",
 *         "role": "owner",
 *         "sex": "unknown",
 *         "title": "",
 *         "user_id": 1844246823
 *     },
 *     "user_id": 1844246823,
 *     "message_id": 530187080
 * }
 */

/**
 * 群聊
 * test4 @將驰 test5 \篮球 test6 [图片]
 * {
 *     "post_type": "message",
 *     "message_type": "group",
 *     "time": 1662977108,
 *     "self_id": 1022269770,
 *     "sub_type": "normal",
 *     "user_id": 1844246823,
 *     "message_id": 1755132894,
 *     "anonymous": null,
 *     "sender": {
 *         "age": 0,
 *         "area": "",
 *         "card": "scarike",
 *         "level": "",
 *         "nickname": "Sc4r1ke",
 *         "role": "owner",
 *         "sex": "unknown",
 *         "title": "",
 *         "user_id": 1844246823
 *     },
 *     "message": "test4 [CQ:at,qq=1022269770] test5 [CQ:face,id=114] test6[CQ:image,file=2c3dd2ffacb942e655cfe291fa55adc9.image,subType=0,url=https://gchat.qpic.cn/gchatpic_new/1844246823/902338238-2685918168-2C3DD2FFACB942E655CFE291FA55ADC9/0?term=3]",
 *     "message_seq": 51,
 *     "raw_message": "test4 [CQ:at,qq=1022269770] test5 [CQ:face,id=114] test6[CQ:image,file=2c3dd2ffacb942e655cfe291fa55adc9.image,subType=0,url=https://gchat.qpic.cn/gchatpic_new/1844246823/902338238-2685918168-2C3DD2FFACB942E655CFE291FA55ADC9/0?term=3]",
 *     "font": 0,
 *     "group_id": 902338238
 * }
 */

@Getter
@Setter
@Accessors(chain = true)
public class QQMessage {
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Sender{
        private String nickname;
        private String card;
        private Long user_id;
    }
    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Anonymous{
        private String name;
    }
    private String post_type;
    private String message_type;
    private String sub_type;
    private Long self_id;
    private Long user_id;
    private Long group_id;
    private Long target_id;
    private String raw_message;
    private Sender sender;
    private Anonymous anonymous;
}
