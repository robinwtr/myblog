<template>
  <div>
    <!-- banner -->
    <div class="about-banner banner">
      <h1 class="banner-title">关于我</h1>
    </div>
    <!-- 关于我内容 -->
    <v-card class="blog-container">
      <div class="my-wrapper">
        <v-avatar size="110">
          <img
            class="author-avatar"
            src="https://www.static.talkxj.com/avatar/blogger.jpg"
          />
        </v-avatar>
      </div>
      <div class="about-content markdown-body" v-html="aboutContent" />
    </v-card>
  </div>
</template>

<script>
export default {
  created() {
    this.getAboutContent();
  },
  data: function() {
    return {
      aboutContent: ""
    };
  },
  methods: {
    getAboutContent() {
      this.axios.get("/api/about").then(({ data }) => {
        const MarkdownIt = require("markdown-it");
        const md = new MarkdownIt();
        this.aboutContent = md.render(data.data);
      });
    }
  }
};
</script>

<style scoped>
.about-banner {
  background: url("https://pcsdata.baidu.com/thumbnail/38ffd3b44o63bb54557b0045077cf42e?fid=4503602915668658-16051585-645012942263778&rt=pr&sign=FDTAER-yUdy3dSFZ0SVxtzShv1zcMqd-SO6gm5ibwTWBKkJxg7ylNCocDAM%3D&expires=2h&chkv=0&chkbd=0&chkpc=&dp-logid=223179933524222521&dp-callid=0&time=1640325600&bus_no=26&size=c1600_u1600&quality=100&vuk=-&ft=video") center center / cover
    no-repeat;
  background-color: #49b1f5;
}
.about-content {
  word-break: break-word;
  line-height: 1.8;
  font-size: 14px;
}
.my-wrapper {
  text-align: center;
}
.author-avatar {
  transition: all 0.5s;
}
.author-avatar:hover {
  transform: rotate(360deg);
}
</style>
