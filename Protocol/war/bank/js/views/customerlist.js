directory.CustomerListView = Backbone.View.extend({

    tagName:'table',
    
    className:'table table-striped',
    
    initialize:function () {
    	_.bindAll(this);
    	this.model.on("reset", this.render, this);
        this.model.on("add", function (customer) {
            this.$el.append(new directory.CustomerListItemView({model:customer}).render().el);
        }, this);
    },

    render:function () {
        this.$el.empty();
        _.each(this.model.models, function (customer) {
            this.$el.append(new directory.CustomerListItemView({model:customer}).render().el);
        }, this);
        return this;
    }
});

directory.CustomerListItemView = Backbone.View.extend({

    tagName:"tr",

    events:{
    	'click' : function(element) {
    			window.location = "#customers/" + this.model.id;
    		},
    	'mouseover' : function(element) {
    			this.$el.addClass('success');
    		},
    	'mouseleave' : function()
    	{
    		this.$el.removeClass('success');
    	}
    },
    
    initialize:function () {
    	_.bindAll(this);
        this.model.on("destroy", this.close, this);
        this.model.on("sync", this.sync, this);
        
        this.$el.css( "cursor", "pointer" );
    },
    
    sync:function()
    {
    	this.render();
    	window.location = '#customers/' + this.model.id;
    },
    
    render:function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }

});